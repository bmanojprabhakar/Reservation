package com.codebees.reservation.controller;

import com.codebees.reservation.dto.Constants;
import com.codebees.reservation.dto.ErrorResponse;
import com.codebees.reservation.dto.ResponseDto;
import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.service.TicketService;
import com.codebees.reservation.service.impl.TicketServiceImpl;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST APIs - Tickets",
        description = "CRUD REST APIs to CREATE, UPDATE AND DELETE ticket details"
)
@RestController
@RequestMapping(path = "/api/v1/tickets/", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Ticket"
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
    @PostMapping("/purchase")
    public ResponseEntity<ResponseDto> purchaseTicket(@Valid @RequestBody TicketsDto ticketRequest) {
        Ticket ticket = ticketService.purchaseTicket(ticketRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(Constants.STATUS_CREATED,
                        "Ticket Created with PNR number "+ticket.getTicketId()));
    }

    @Operation(
            summary = "Print Ticket Details REST API",
            description = "REST API to generate receipt &  print ticket details based on PNR number"
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
    @GetMapping("/receipt/{pnrNumber}")
    public ResponseEntity<TicketsDto> printReceipt(@PathVariable Long pnrNumber) {
        TicketsDto ticketsDto = ticketService.generateTicketReceipt(pnrNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketsDto);
    }

    @Operation(
            summary = "Delete Ticket REST API",
            description = "REST API to delete ticket & seats of a PNR number"
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
    @DeleteMapping("/cancel/{pnrNumber}")
    public ResponseEntity<ResponseDto> cancelTicket(@PathVariable Long pnrNumber) {
        ticketService.cancelTicket(pnrNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(Constants.STATUS_SUCCESS, Constants.SUCCESS_MESSAGE));
    }

}
