package com.example.cinemaapi;

import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.model.user.UserRoles;
import com.example.cinemaapi.repository.bill.PaymentRepository;
import com.example.cinemaapi.util.DateUtils;
import com.example.cinemaapi.repository.user.RoleRepository;
import com.example.cinemaapi.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CinemaApiSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApiSystemApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**").allowedOrigins("*").allowedMethods("PUT", "POST", "GET", "DELETE", "OPTIONS");
                registry.addMapping("/api/v1/auth/**").allowedOrigins("*").allowedMethods("PUT", "POST", "GET", "DELETE", "OPTIONS");
            }
        };
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, PaymentRepository paymentRepository) {
        return args -> {
            //Create Admin and Passenger Roles
            Role adminRole = roleRepository.findByCode(UserRoles.ADMIN);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setCode(UserRoles.ADMIN);
                adminRole.setCode(UserRoles.ADMIN);
                adminRole.setName("Quản trị hệ thống");
                roleRepository.save(adminRole);
            }

            Role staffRole = roleRepository.findByCode(UserRoles.STAFF);
            if (staffRole == null) {
                staffRole = new Role();
                staffRole.setCode(UserRoles.STAFF);
                staffRole.setName("Nhân viên");
                roleRepository.save(staffRole);
            }

            Role userRole = roleRepository.findByCode(UserRoles.USER);
            if (userRole == null) {
                userRole = new Role();
                userRole.setCode(UserRoles.USER);
                userRole.setName("Người dùng");
                roleRepository.save(userRole);
            }

            //Create an Admin user
            User admin = userRepository.findByEmail("admin@gmail.com");
            if (admin == null) {
                admin = new User()
                    .setEmail("admin@gmail.com")
                    .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                    .setFullName("Admin")
                    .setStatus(1)
                    .setRoles(Arrays.asList(adminRole));

                admin.setCreatedBy(null);
                admin.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());

                userRepository.save(admin);
            } else {
                Payment payment = admin.getPayment();
                if (payment == null) {
                    Payment newPayment = new Payment().setAmount(0).setTotalAmount(0);
                    newPayment.setCreatedBy(null);
                    newPayment.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());

                    Payment paymentCreated = paymentRepository.save(newPayment);

                    admin.setPayment(paymentCreated);
                    userRepository.save(admin);
                }
            }

            //Create an Staff user
            User staff = userRepository.findByEmail("staff@gmail.com");
            if (staff == null) {
                staff = new User()
                    .setEmail("staff@gmail.com")
                    .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                    .setFullName("Staff")
                    .setStatus(1)
                    .setRoles(Arrays.asList(staffRole));

                staff.setCreatedBy(null);
                staff.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());

                userRepository.save(staff);
            } else {
                Payment payment = staff.getPayment();
                if (payment == null) {
                    Payment newPayment = new Payment().setAmount(0).setTotalAmount(0);
                    newPayment.setCreatedBy(null);
                    newPayment.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());

                    Payment paymentCreated = paymentRepository.save(newPayment);

                    staff.setPayment(paymentCreated);
                    userRepository.save(staff);
                }
            }

            //Create a user user
            User customer = userRepository.findByEmail("customer@gmail.com");
            if (customer == null) {
                customer = new User()
                        .setEmail("customer@gmail.com")
                        .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFullName("Customer")
                        .setStatus(1)
                        .setRoles(Arrays.asList(userRole));

                customer.setCreatedBy(null);
                customer.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());

                userRepository.save(customer);
            } else {
                Payment payment = customer.getPayment();
                if (payment == null) {
                    Payment newPayment = new Payment().setAmount(0).setTotalAmount(0);
                    newPayment.setCreatedBy(null);
                    newPayment.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());

                    Payment paymentCreated = paymentRepository.save(newPayment);

                    customer.setPayment(paymentCreated);
                    userRepository.save(customer);
                }
            }
        };
    }
}
