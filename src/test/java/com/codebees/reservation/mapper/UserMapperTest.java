package com.codebees.reservation.mapper;

import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserMapperTest {

    @Test
    void testMapToUser() {
        UserDto userDto = new UserDto("Manoj", "Prabhakar", "m.p@t.com");
        Users users = UserMapper.mapToUser(userDto, new Users());

        assertThat(userDto.getEmail()).isEqualTo(users.getEmail());
        assertThat(userDto.getFirstName()).isEqualTo(users.getFirstName());
        assertThat(userDto.getLastName()).isEqualTo(users.getLastName());
    }

    @Test
    void testMapToUserDto() {
        Users users = new Users(1L, "Manoj", "Prabhakar", "m.p@t.com", List.of(new Ticket()));
        UserDto userDto = UserMapper.mapToUserDto(users, new UserDto());

        assertThat(users.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(users.getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(users.getLastName()).isEqualTo(userDto.getLastName());
    }
}