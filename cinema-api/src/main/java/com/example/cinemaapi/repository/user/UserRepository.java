package com.example.cinemaapi.repository.user;

import com.example.cinemaapi.model.user.User;
import org.springframework.data.repository.CrudRepository;

/**
 * .
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByVerifyCode(String verifyCode);

}
