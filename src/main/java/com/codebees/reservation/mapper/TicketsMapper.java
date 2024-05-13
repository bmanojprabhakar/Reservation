package com.codebees.reservation.mapper;

import com.codebees.reservation.dto.SeatsDto;
import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TicketsMapper {
    private static final String className = TicketsMapper.class.getSimpleName();

    /**
     * Converts incoming Ticket request to a required entity suitable for DB
     * @param ticketRequest - API object
     * @param ticket - Entity
     * @return - Entity
     */
    public static Ticket mapToTicket(TicketsDto ticketRequest, Ticket ticket) {
        log.debug("Entering {}:mapToTicket()", className);
        ticket.setFromLocation(ticketRequest.getFrom());
        ticket.setToLocation(ticketRequest.getTo());
        ticket.setPricePaid(ticketRequest.getPrice());

        UserDto userDto = ticketRequest.getUsers();
        Users users = UserMapper.mapToUser(userDto, new Users());
        users.setTicket(List.of(ticket));
        ticket.setUser(users);

        ticket.setSeat(SeatMapper.mapSeatsFromTicket(ticket, ticketRequest));
        log.debug("Exiting {}:mapToTicket()", className);
        return ticket;
    }

    /**
     * Forms an object suitable for API response from data in DB
     * @param ticket - JPA entity
     * @param ticketsDto - API response
     * @return - API response object
     */
    public static TicketsDto mapToTicketsDto(Ticket ticket, TicketsDto ticketsDto) {
        log.debug("Entering {}:mapToTicketsDto()", className);
        ticketsDto.setFrom(ticket.getFromLocation());
        ticketsDto.setTo(ticket.getToLocation());
        ticketsDto.setPrice(ticket.getPricePaid());

        UserDto userDto = UserMapper.mapToUserDto(ticket.getUser(), new UserDto());
        ticketsDto.setUsers(userDto);

        List<SeatsDto> seatDto = SeatMapper.mapToSeatsDto(ticket.getSeat());
        ticketsDto.setSeats(seatDto);
        log.debug("Exiting {}:mapToTicketsDto()", className);
        return ticketsDto;
    }
}
