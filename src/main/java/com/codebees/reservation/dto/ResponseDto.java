package com.codebees.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "API Response", description = "Schema to hold the successful API transactions")
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
}
