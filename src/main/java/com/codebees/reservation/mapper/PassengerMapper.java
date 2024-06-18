package com.codebees.reservation.mapper;

import com.codebees.reservation.dto.PassengerDto;
import com.codebees.reservation.entity.Passenger;

public class PassengerMapper {
    public static Passenger mapToPassenger(PassengerDto passengerDto, Passenger passenger) {
        passenger.setFirstName(passengerDto.getFirstName());
        passenger.setLastName(passengerDto.getLastName());
        passenger.setAge(passengerDto.getAge());
        return passenger;
    }
}
