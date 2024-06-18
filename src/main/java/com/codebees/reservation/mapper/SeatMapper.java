package com.codebees.reservation.mapper;

import com.codebees.reservation.dto.SeatsDto;
import com.codebees.reservation.dto.SectionsDto;
import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SeatMapper {
    private static final String className = SeatMapper.class.getSimpleName();

    /**
     * Forms a seat object automatically for every ticket
     * @param ticket - Entity
     * @param ticketsDto - API
     * @return - List of Seat Entity
     */
    /*public static List<Seat> mapSeatsFromTicket(Ticket ticket, TicketsDto ticketsDto, int seatNumber) {
        log.debug("Entering {}:mapSeatsFromTicket()", className);
        List<Seat> finalSeat = new ArrayList<>();
        List<Seat> seatsFromTicket = mapToSeats(ticketsDto.getSeats());
        for(Seat seat: seatsFromTicket) {
            Seat mappedSeat = new Seat();
            mappedSeat.setTicket(ticket);
            mappedSeat.setSection(seat.getSection());
            mappedSeat.setSeatNo(++seatNumber);
            finalSeat.add(mappedSeat);
        }
        log.debug("Exiting {}:mapSeatsFromTicket()", className);
        return finalSeat;
    }*/

    /**
     * Converts seat entity to API response object
     * @param seats - Entity list
     * @return - API response list
     */
    /*public static List<SeatsDto> mapToSeatsDto(List<Seat> seats) {
        log.debug("Entering {}:mapToSeatsDto()", className);
        List<SeatsDto> seatsDto = new ArrayList<>();
        for(Seat seat: seats) {
            SeatsDto seatDto = new SeatsDto();
            seatDto.setSection(seat.getSection());
            seatsDto.add(seatDto);
        }
        log.debug("Exiting {}:mapToSeatsDto()", className);
        return seatsDto;
    }*/

    /**
     * Converts API request to entity object to persist in DB
     * @param seatsDto - API object
     * @return - entity
     */
    /*public static List<Seat> mapToSeats(List<SeatsDto> seatsDto) {
        log.debug("Entering {}:mapToSeats()", className);
        List<Seat> seats = new ArrayList<>();
        for(SeatsDto seatDto: seatsDto) {
            Seat seat = new Seat();
            seat.setSection(seatDto.getSection());
            seats.add(seat);
        }
        log.debug("Exiting {}:mapToSeats()", className);
        return seats;
    }*/

    /**
     * Forms a customized object which holds information to populate user, ticket and seat data
     * @param reservedSeats - list of seats
     * @return - API object
     */
    public static List<SectionsDto> filterSeatsBySection(List<Seat> reservedSeats) {
        log.debug("Entering {}:filterSeatsBySection()", className);
        List<SectionsDto> sections = new ArrayList<>();

        for(Seat seat : reservedSeats) {
            SectionsDto section = new SectionsDto();
            Ticket ticket = seat.getTicket();

            section.setFirstName(ticket.getUser().getFirstName());
            section.setSeatId(seat.getSeatId());
            section.setSection(seat.getSection());
            sections.add(section);
        }
        log.debug("Exiting {}:filterSeatsBySection()", className);
        return sections;
    }
}
