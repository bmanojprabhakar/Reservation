package com.codebees.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "passengers", description = "schema to hold list of all passengers in a ticket")
public class PassengerDto {
    @NotEmpty
    @Schema(name = "First Name", description = "First name of the passenger")
    private String firstName;

    @Schema(name = "Last Name", description = "Last name of the passenger")
    private String lastName;

    @Min(value = 1, message = "Minimum value of age should be 1")
    @Max(value = 150, message = "Maximum value of age cannot be greater than 150")
    @Schema(name = "age", description = "Age of the passenger")
    @Digits(integer = 2, fraction = 0)
    private Integer age;
}
