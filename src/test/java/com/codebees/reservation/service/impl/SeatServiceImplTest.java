package com.codebees.reservation.service.impl;

import com.codebees.reservation.dto.SeatsDto;
import com.codebees.reservation.dto.SectionsDto;
import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Section;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import com.codebees.reservation.exception.SeatNotFoundException;
import com.codebees.reservation.repository.SeatRepository;
import com.codebees.reservation.service.SeatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SeatServiceImplTest {
    @Mock
    SeatRepository seatRepository;

    private SeatService seatService;
    AutoCloseable autoCloseable;
    SeatsDto seatsDto;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        seatService = new SeatServiceImpl(seatRepository);
        seatsDto = new SeatsDto();
        seatsDto.setSection(Section.SECTION_A);
    }

    /*@Test
    void testFetchSeatDetails() {
        mock(SeatRepository.class);
        List<Seat> seats = new ArrayList<>();

        Users users = new Users();
        users.setFirstName("Manoj");
        Ticket ticket = new Ticket();
        ticket.setUser(users);
        Seat seat = new Seat(1L, Section.SECTION_A, ticket);
        seats.add(seat);

        Mockito.when(seatRepository.findBySection(Section.SECTION_A)).thenReturn(seats);
        List<SectionsDto> sectionsDto = seatService.fetchSeatDetails(Section.SECTION_A);
        assertThat(sectionsDto.get(0).getSection()).isEqualTo(Section.SECTION_A);
    }*/

    @Test
    void testModifySeat_throwsException() {
        Mockito.when(seatRepository.findBySeatId(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> seatService.modifySeat(1L, Section.SECTION_A)).isInstanceOf(SeatNotFoundException.class);
    }

    @Test
    void testModifySeat_success() {
        mock(SeatRepository.class);
        when(seatRepository.findBySeatId(1L)).thenReturn(Optional.of(new Seat()));

        assertThatNoException().isThrownBy(() -> seatService.modifySeat(1L, Section.SECTION_A));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
}