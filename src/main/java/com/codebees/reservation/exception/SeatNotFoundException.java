package com.codebees.reservation.exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(String s) {
        super(s);
    }
}
