package com.codebees.reservation.controller;

import com.codebees.reservation.dto.Constants;
import com.codebees.reservation.dto.ErrorResponse;
import com.codebees.reservation.dto.ResponseDto;
import com.codebees.reservation.dto.SectionsDto;
import com.codebees.reservation.entity.Section;
import com.codebees.reservation.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "REST APIs - Seats",
        description = "REST APIs to fetch seats by section and modify seats for a ticket"
)
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/seats/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SeatController {
    private final SeatService seatService;

    @Operation(
            summary = "REST API to fetch seat details for a section",
            description = "It lets us to view the users and seat they are allocated by the requested section"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
    @GetMapping("/seatsBySection")
    public ResponseEntity<List<SectionsDto>> fetchDetailsForASection(@RequestParam("section") Section section) {
        List<SectionsDto> sectionsDto = seatService.fetchSeatDetails(section);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sectionsDto);
    }

    @Operation(
            summary = "An API to modify a user's seat",
            description = "REST API to modify user's seat section based on PNR number, seat number and required section"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PatchMapping("/{seatId}/modifyTo")
    public ResponseEntity<ResponseDto> modifySeat(@PathVariable Long seatId,
                                                  @RequestParam("section") Section section) {
        seatService.modifySeat(seatId, section);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(Constants.STATUS_SUCCESS, Constants.SUCCESS_MESSAGE));
    }

    // Limit the seats based on section
}
