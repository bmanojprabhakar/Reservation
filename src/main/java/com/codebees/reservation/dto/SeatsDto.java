package com.codebees.reservation.dto;

import com.codebees.reservation.entity.Section;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Seats", description = "Schema to hold Seat information")
public class SeatsDto {
    @Schema(description = "Seat details", example = "SECTION_A")
    @NotNull(message = "Section name of a train is mandatory")
    private Section section;
}
