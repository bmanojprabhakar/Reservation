package com.codebees.reservation.service.impl;

import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.entity.Users;
import com.codebees.reservation.exception.UserAlreadyExistsException;
import com.codebees.reservation.repository.UserRepository;
import com.codebees.reservation.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    AutoCloseable autoCloseable;
    UserDto userDto;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
        userDto = new UserDto("Manoj", "Prabhakar", "m.p@t.com");
    }

    @Test
    public void testCreateUser_Success() {
        mock(UserRepository.class);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.createUser(userDto);

        assertThatNoException().isThrownBy(() -> userService.createUser(userDto));
    }

    @Test
    public void testCreateUser_EmailAlreadyExists() {
        mock(UserRepository.class);
        Users existingUser = new Users();
        existingUser.setFirstName("Manoj");
        existingUser.setEmail(userDto.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> userService.createUser(userDto)).isInstanceOf(UserAlreadyExistsException.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

}