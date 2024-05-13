package com.codebees.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User", description = "Schema to hold User information")
public class UserDto {
    @Schema(description = "First Name of the user", example = "Manoj")
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 25, message = "The length of the first name should be between 5 and 25")
    private String firstName;

    @Schema(description = "Last Name of the user", example = "Prabhakar")
    @Size(max = 25, message = "The length of the last name should not exceed 25")
    private String lastName;

    @Schema(description = "Email of the user", example = "manoj@test.com")
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;
}
