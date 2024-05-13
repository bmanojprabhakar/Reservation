package com.codebees.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name = "Error Response", description = "Schema to hold error response information")
@Data @AllArgsConstructor
public class ErrorResponse {
    @Schema(description = "API path invoked by client")
    private  String apiPath;

    @Schema(description = "Error code describing the error")
    private HttpStatus errorCode;

    @Schema(description = "Error message describing the error")
    private  String errorMessage;

    @Schema(description = "Timestamp of the error")
    private LocalDateTime errorTime;
}
