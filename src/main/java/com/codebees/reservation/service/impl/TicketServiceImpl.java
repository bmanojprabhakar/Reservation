package com.codebees.reservation.service.impl;

import com.codebees.reservation.dto.Constants;
import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.entity.Passenger;
import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Section;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import com.codebees.reservation.exception.SeatNotFoundException;
import com.codebees.reservation.exception.TicketNotFoundException;
import com.codebees.reservation.exception.UserNotFoundException;
import com.codebees.reservation.mapper.PassengerMapper;
import com.codebees.reservation.mapper.TicketsMapper;
import com.codebees.reservation.repository.PassengerRepository;
import com.codebees.reservation.repository.SeatRepository;
import com.codebees.reservation.repository.TicketRepository;
import com.codebees.reservation.repository.UserRepository;
import com.codebees.reservation.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final String className = this.getClass().getSimpleName();
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final PassengerRepository passengerRepository;

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

        List<Seat> seatsInASection = seatRepository.findEmptySeatsInASection(ticketRequest.getSection());
        if(seatsInASection.isEmpty() ||
                (ticketRequest.getPassengers().size() > seatsInASection.size())) {
            throw new SeatNotFoundException(String.format("Only %s seats are available in section %s " +
                    "Unable to book for %s passengers", seatsInASection.size(),
                    ticketRequest.getSection().name(), ticketRequest.getPassengers().size()));
        }

        List<Passenger> passengers = ticketRequest.getPassengers().stream()
                .map(p -> PassengerMapper.mapToPassenger(p, new Passenger()))
                .toList();
        passengerRepository.saveAll(passengers);

        Ticket ticket = TicketsMapper.mapToTicket(ticketRequest, new Ticket());
        ticket.setUser(user.get());

        IntStream.range(0, passengers.size())
                .forEach(i -> {
                    seatsInASection.get(i).setPassenger(passengers.get(i));
                    seatsInASection.get(i).setTicket(ticket);
                });

        ticket.setSeat(seatsInASection);
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

        List<Seat> seats = seatRepository.findByTicket_TicketId(existingTicket.get().getTicketId());

        // Disassociate tickets and passengers from seat
        seats.forEach(s -> {
            s.setPassenger(null);
            s.setTicket(null);
        });
        seatRepository.saveAll(seats);

        ticketRepository.delete(existingTicket.get());
        log.debug("Exiting {}:cancelTicket()", className);
    }
}
