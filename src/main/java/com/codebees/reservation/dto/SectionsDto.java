package com.codebees.reservation.dto;

import com.codebees.reservation.entity.Section;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Section", description = "Schema to hold section-wise seat information")
public class SectionsDto {
    @Schema(description = "First Name of the user", example = "Manoj")
    private String firstName;

    @Schema(description = "Seat number", example = "1")
    private Long seatId;

    @Schema(description = "Seat details", example = "SECTION_A")
    private Section section;
}
