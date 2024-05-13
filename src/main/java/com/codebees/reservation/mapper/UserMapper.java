package com.codebees.reservation.mapper;

import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.entity.Users;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMapper {
    private static final String className = UserMapper.class.getSimpleName();
    /**
     * Converts incoming user registration details to a JPA
     * @param userDto - API Pojo
     * @param users - JPA entity
     * @return - Users object
     */
    public static Users mapToUser(UserDto userDto, Users users) {
        log.debug("Entering {}:mapToUser()", className);
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setEmail(userDto.getEmail());
        log.debug("Exiting {}:mapToUser()", className);
        return users;
    }

    /**
     * Converts User entity to an API response object
     * @param user - JPA entity
     * @param userDto - API object
     * @return - API object
     */
    public static UserDto mapToUserDto(Users user, UserDto userDto) {
        log.debug("Entering {}:mapToUserDto()", className);
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        log.debug("Exiting {}:mapToUserDto()", className);
        return userDto;
    }
}
