package com.example.cinemaapi.service;

import com.example.cinemaapi.controller.v1.api.UserController;
import com.example.cinemaapi.dto.mapper.MovieMapper;
import com.example.cinemaapi.dto.mapper.UserMapper;
import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.bill.BillDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
//import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.model.user.UserRoles;
import com.example.cinemaapi.repository.bill.BillRepository;
import com.example.cinemaapi.repository.bill.PaymentRepository;
import com.example.cinemaapi.repository.user.RoleRepository;
import com.example.cinemaapi.repository.user.UserRepository;
//import com.example.cinemaapi.repository.bill.PaymentRepository;
import com.example.cinemaapi.service.bill.BillDetailService;
import com.example.cinemaapi.service.bill.BillService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.MOVIE;
import static com.example.cinemaapi.exception.EntityType.USER;
import static com.example.cinemaapi.exception.ExceptionType.*;
import static com.example.cinemaapi.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.cinemaapi.security.SecurityConstants.SECRET;

/**
 * .
 */
@Component
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto verify(String verifyCode) {
        Role userRole;
        Optional<User> user = Optional.ofNullable(userRepository.findByVerifyCode(verifyCode));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setVerifyCode("")
                    .setStatus(1);

            Instant now = Instant.now().atZone(ZoneOffset.systemDefault()).toInstant();
            userModel.setModifiedDate(now);

            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND);
    }

    @Override
    public UserDto signup(UserDto userDto, UserDto userCreateDto) {
        List<Role> userRoles = new ArrayList<Role>();
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            if (userDto.getRoles() == null) {
                if (userDto.isAdmin()) {
                    Role role = roleRepository.findByCode(UserRoles.ADMIN);
                    userRoles.add(role);
                } else {
                    Role role = roleRepository.findByCode(UserRoles.USER);
                    userRoles.add(role);
                }
            } else  {
                List<Long> roleIdList = new ArrayList<>();

                for (RoleDto role: userDto.getRoles()) {
                    roleIdList.add(role.getId());
                }

                Iterable<Role> roles = roleRepository.findByIdIn(roleIdList);

                for (Role role: roles) {
                    userRoles.add(new ModelMapper().map(role, Role.class));
                }
            }

            Payment payment = new Payment().setAmount(0).setTotalAmount(0);
            Payment paymentCreated = paymentRepository.save(payment);

            user = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRoles(userRoles)
                    .setFullName(userDto.getFullName())
                    .setVerifyCode(userDto.getVerifyCode())
                    .setPayment(paymentCreated)
                    .setStatus(userDto.getStatus());

            user.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            if (userCreateDto != null) {
                user.setCreatedBy(userCreateDto.getId());
            }

            return UserMapper.toUserDto(userRepository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, userDto.getEmail());
    }

    @Override
    public UserDto signin(String email, String password) {
        Role userRole;
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
                throw exception(USER, WRONG_PASSWORD, email);
            } else if (user.getStatus() != 1) {
                throw exception(USER, USER_NOT_VERIFY_YET, email);
            } else {
                Claims claims = Jwts.claims().setSubject(user.getEmail());

                Collection<Role> userRoles = user.getRoles();
                List<UserRoles> roles = new ArrayList<>();

                userRoles.forEach(role -> {
                    roles.add(role.getCode());
                });

                claims.put("roles", roles);

                String token = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();

                UserDto userDto = UserMapper.toUserDto(user);
                userDto.setToken(token);

                return userDto;
            }
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Transactional
    public UserDto getUserDetailByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            UserDto userDto = UserMapper.toUserDto(user.get());
            return userDto;
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Transactional
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    /**
     * Update User Profile
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setFullName(userDto.getFullName());

            userModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            userModel.setModifiedBy(userDto.getId());

            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    /**
     * Change Password
     *
     * @param userDto
     * @param newPassword
     * @return
     */
    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));

            userModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            userModel.setModifiedBy(userDto.getId());

            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public Set<UserDto> getAllUsers() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Transactional
    public UserDto updateUser(UserDto userDto, UserDto userModifyDto) {
        Optional<User> user = userRepository.findById(userDto.getId());
        if (user.isPresent()) {
            User userModel = user.get();
            if (!userModel.getEmail().isEmpty()) {
                userModel.setEmail(userDto.getEmail());
            }
            if (!userDto.getPassword().isEmpty() && userDto.getPassword() != userModel.getPassword()) {
                userModel.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            }
            if (!userDto.getFullName().isEmpty()) {
                userModel.setFullName(userDto.getFullName());
            }
            if (userDto.getStatus() != null) {
                userModel.setStatus(userDto.getStatus());
            }
            if (userDto.getRoles() != null) {
                List<Long> roleIdList = new ArrayList<>();

                for (RoleDto role: userDto.getRoles()) {
                    roleIdList.add(role.getId());
                }

                Iterable<Role> roles = roleRepository.findAllById(roleIdList);
                List<Role> userRoles = new ArrayList<>();

                for (Role role: roles) {
                    userRoles.add(role);
                }

                userModel.setRoles(userRoles);
            }

            userModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            userModel.setModifiedBy(userModifyDto.getId());

            return modelMapper.map(userRepository.save(userModel), UserDto.class);
        }

        throw exceptionWithId(USER, ENTITY_NOT_FOUND, 2, userDto.getId().toString());
    }

    @Transactional
    public void deleteUser(Long userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user != null && user.isPresent()) {
            User userModel = user.get();
            userModel.setRoles(null);
            userRepository.save(userModel);

            Payment payment = userModel.getPayment();
            if (payment != null) {
                paymentRepository.deleteById(payment.getId());
            }

            Set<BillDetailDto> billDetailDtos = billDetailService.getAllBillDetails(userID, false);
            for (BillDetailDto billDetailDto: billDetailDtos) {
                billDetailDto.setBill(null);
                billDetailService.updateBillDetail(billDetailDto);
                billDetailService.deleteBillDetail(billDetailDto.getId());
            }

            Set<BillDto> billDtos = billService.getAllBills(userID, false);
            for (BillDto billDto: billDtos) {
                billService.deleteBill(billDto.getId());
            }

            userRepository.deleteById(userID);
        } else {
            throw exceptionWithId(USER, ENTITY_NOT_FOUND, 2, userID.toString());
        }
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return BRSException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
