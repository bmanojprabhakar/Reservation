package com.codebees.reservation.service.impl;

import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.entity.Users;
import com.codebees.reservation.exception.UserAlreadyExistsException;
import com.codebees.reservation.mapper.UserMapper;
import com.codebees.reservation.repository.UserRepository;
import com.codebees.reservation.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final String className = this.getClass().getSimpleName();
    private UserRepository userRepository;

    /**
     * User registration - creates new user with email
     * @param userDto - API object
     */
    public void createUser(UserDto userDto) {
        log.debug("Entering {}:createUser()", className);
        Users users = UserMapper.mapToUser(userDto, new Users());
        Optional<Users> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with the given email "+userDto.getEmail());
        }
        userRepository.save(users);
        log.debug("Exiting {}:createUser()", className);
    }
}