package com.codebees.reservation.controller;

import com.codebees.reservation.dto.Constants;
import com.codebees.reservation.dto.ErrorResponse;
import com.codebees.reservation.dto.ResponseDto;
import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST APIs - Users",
        description = "It enables to CREATE user or user registration for booking, modifying and cancelling tickets"
)
@RestController
@RequestMapping(path = "/api/v1/users/", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        Constants.STATUS_CREATED,
                        Constants.STATUS_CREATED));
    }

}
