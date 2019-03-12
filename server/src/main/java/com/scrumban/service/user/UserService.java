package com.scrumban.service.user;

import com.scrumban.exception.EmailAlreadyExistsException;
import com.scrumban.model.domain.User;
import com.scrumban.repository.UserRepository;
import com.scrumban.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserValidator userValidator;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userValidator = userValidator;
    }

    public User saveNewUser(User newUser){
        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setConfirmPassword("");
            User savedUser = userRepository.save(newUser);
            return savedUser;
        }catch(Exception e){
            log.info("there was a problem creating the user");
            throw new EmailAlreadyExistsException("Email already exists!");
        }
    }


    public void validateUserDetails(User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
    }

    public User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    public User getUser(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return user.get();
    }
}
