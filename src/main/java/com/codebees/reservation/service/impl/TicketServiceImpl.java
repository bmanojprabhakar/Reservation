package com.codebees.reservation.service.impl;

import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import com.codebees.reservation.exception.TicketNotFoundException;
import com.codebees.reservation.exception.UserNotFoundException;
import com.codebees.reservation.mapper.TicketsMapper;
import com.codebees.reservation.repository.TicketRepository;
import com.codebees.reservation.repository.UserRepository;
import com.codebees.reservation.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {
    private final String className = this.getClass().getSimpleName();
    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    /**
     * Reserve ticket - allows passenger to reserve a ticket in their preferred section
     * @param ticketRequest - API
     * @return - Entity
     */
    @Override
    public Ticket purchaseTicket(TicketsDto ticketRequest) {
        log.debug("Entering {}:purchaseTicket()", className);
        Optional<Users> user = userRepository.findByEmail(ticketRequest.getUsers().getEmail());

        if(user.isEmpty()) {
            throw new UserNotFoundException("Given user is not registered with the email "+ticketRequest.getUsers().getEmail());
        }

        Ticket ticket = TicketsMapper.mapToTicket(ticketRequest, new Ticket());
        ticket.setUser(user.get());
        log.debug("Exiting {}:purchaseTicket()", className);
        return ticketRepository.save(ticket);
    }

    /**
     * Fetches required data to print ticket receipt for a reserved ticket
     * @param pnrNumber - reservation id
     * @return - API response object
     */
    @Override
    public TicketsDto generateTicketReceipt(Long pnrNumber) {
        log.debug("Entering {}:generateTicketReceipt()", className);
        Optional<Ticket> ticket = ticketRepository.findByTicketId(pnrNumber);

        if(ticket.isEmpty()) {
            throw new TicketNotFoundException("Given PNR number "+pnrNumber+" is invalid");
        }
        log.debug("Exiting {}:generateTicketReceipt()", className);
        return TicketsMapper.mapToTicketsDto(ticket.get(), new TicketsDto());
    }

    /**
     * Ticket cancellation - Removes ticket from the system (Hard delete)
     * @param pnrNumber - reservation id
     */
    @Override
    public void cancelTicket(Long pnrNumber) {
        log.debug("Entering {}:cancelTicket()", className);
        Optional<Ticket> existingTicket = ticketRepository.findByTicketId(pnrNumber);

        if(existingTicket.isEmpty()) {
            throw new TicketNotFoundException("Given PNR number "+pnrNumber+" is invalid");
        }

        ticketRepository.delete(existingTicket.get());
        log.debug("Exiting {}:cancelTicket()", className);
    }
}
