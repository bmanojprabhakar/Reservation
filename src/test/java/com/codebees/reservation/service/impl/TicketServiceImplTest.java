package com.codebees.reservation.service.impl;

import com.codebees.reservation.dto.SeatsDto;
import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.entity.Section;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import com.codebees.reservation.exception.TicketNotFoundException;
import com.codebees.reservation.exception.UserNotFoundException;
import com.codebees.reservation.mapper.TicketsMapper;
import com.codebees.reservation.repository.TicketRepository;
import com.codebees.reservation.repository.UserRepository;
import com.codebees.reservation.service.TicketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketServiceImplTest {
    @Mock
    TicketRepository ticketRepository;

    @Mock
    UserRepository userRepository;

    private TicketService ticketService;
    AutoCloseable autoCloseable;
    TicketsDto ticketsDto;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        ticketService = new TicketServiceImpl(ticketRepository, userRepository);
        ticketsDto = new TicketsDto();
        ticketsDto.setFrom("Chicago");
        ticketsDto.setTo("New York");
        ticketsDto.setPrice(100.0);
        UserDto userDto = new UserDto("Manoj", "Prabhakar", "m.p@t.com");
        ticketsDto.setUsers(userDto);
        SeatsDto seatsDto = new SeatsDto();
        seatsDto.setSection(Section.SECTION_A);
        ticketsDto.setSeats(List.of(seatsDto));
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testPurchaseTicket_throwsException() {
        mock(UserRepository.class);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ticketService.purchaseTicket(ticketsDto)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testPurchaseTicket_success() {
        mock(UserRepository.class);
        mock(TicketRepository.class);

        Users user = new Users(1L, "Manoj", "Prabhakar", "m.p@t.com", List.of(new Ticket()));

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        assertThatNoException().isThrownBy(() -> ticketService.purchaseTicket(ticketsDto));

    }

    @Test
    void testGenerateTicketReceipt_success() {
        mock(TicketRepository.class);
        Ticket ticket = TicketsMapper.mapToTicket(ticketsDto, new Ticket());
        when(ticketRepository.findByTicketId(1L)).thenReturn(Optional.of(ticket));

        assertThatNoException().isThrownBy(() -> ticketService.generateTicketReceipt(1L));
    }

    @Test
    void testGenerateTicketReceipt_throwsException() {
        mock(TicketRepository.class);
        when(ticketRepository.findByTicketId(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ticketService.generateTicketReceipt(1L)).isInstanceOf(TicketNotFoundException.class);
    }

    @Test
    void testCancelTicket_successScenario() {
        mock(TicketRepository.class);
        Ticket ticket = TicketsMapper.mapToTicket(ticketsDto, new Ticket());
        when(ticketRepository.findByTicketId(1L)).thenReturn(Optional.of(ticket));

        assertThatNoException().isThrownBy(() -> ticketService.cancelTicket(1L));
    }

    @Test
    void testCancelTicket_throwsException() {
        mock(TicketRepository.class);
        when(ticketRepository.findByTicketId(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ticketService.cancelTicket(1L)).isInstanceOf(TicketNotFoundException.class);
    }
}