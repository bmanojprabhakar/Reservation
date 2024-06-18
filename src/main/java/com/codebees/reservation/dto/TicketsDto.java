package com.codebees.reservation.dto;

import com.codebees.reservation.entity.Section;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(name = "Tickets", description = "Schema to hold Ticket information")
public class TicketsDto {
    @Schema(description = "Source station name", example = "Central Market")
    @NotEmpty(message = "From station cannot be a null or empty")
    @Size(min = 3, max = 25, message = "The length of the from field should be between 5 and 25")
    private String from;

    @Schema(description = "Destination station name", example = "Cantonment")
    @NotEmpty(message = "To station cannot be a null or empty")
    @Size(min = 3, max = 25, message = "The length of the to field should be between 5 and 25")
    private String to;

    @Schema(description = "Ticket price", example = "25.0")
    @NotNull(message = "Price is mandatory")
    @Min(value = 0L, message = "Price value must be positive")
    @Digits(integer = 5, fraction = 2)
    private Double price;

    @Schema(description = "User details")
    @NotNull(message = "User details are mandatory")
    private UserDto users;

    @Schema(description = "Section details", example = "SECTION_A")
    @NotNull(message = "Section which is mandatory for ticket purchase")
    private Section section;

    @Schema(description = "Passenger details", example = "firstName: manoj, lastName: p, age: 22")
    @NotNull(message = "Passenger details are mandatory for ticket booking")
    private List<PassengerDto> passengers;
}
