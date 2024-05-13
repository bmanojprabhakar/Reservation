package com.codebees.reservation.mapper;

import com.codebees.reservation.dto.SeatsDto;
import com.codebees.reservation.dto.SectionsDto;
import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Section;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.codebees.reservation.mapper.SeatMapper.filterSeatsBySection;
import static com.codebees.reservation.mapper.SeatMapper.mapSeatsFromTicket;
import static com.codebees.reservation.mapper.SeatMapper.mapToSeats;
import static com.codebees.reservation.mapper.SeatMapper.mapToSeatsDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SeatMapperTest {

    @Test
    void testMapSeatsFromTicket() {
        Ticket ticket = new Ticket();
        TicketsDto ticketsDto = new TicketsDto();

        ticket.setFromLocation("London");
        ticket.setToLocation("France");
        ticket.setPricePaid(25.0);

        SeatsDto seatsDto = new SeatsDto();
        seatsDto.setSection(Section.SECTION_A);
        List<SeatsDto> seatsFromDto = List.of(seatsDto);
        ticketsDto.setSeats(seatsFromDto);

        List<Seat> finalSeats = mapSeatsFromTicket(ticket, ticketsDto);

        assertThat(finalSeats).isNotNull();
        assertThat(finalSeats.size()).isEqualTo(seatsFromDto.size());

        for (Seat finalSeat : finalSeats) {
            assertThat(finalSeat.getTicket()).isEqualTo(ticket);
            assertThat(finalSeat.getSection()).isEqualTo(finalSeat.getSection());
        }
    }

    @Test
    void testMapToSeatsDto() {
        List<Seat> seats = Arrays.asList(new Seat(1L, Section.SECTION_A, new Ticket()),
                new Seat(2L, Section.SECTION_B, new Ticket()));
        List<SeatsDto> seatsDto = mapToSeatsDto(seats);

        assertThat(seatsDto).isNotNull();
        assertThat(seatsDto.size()).isEqualTo(seats.size());

        for (int i = 0; i < seats.size(); i++) {
            assertThat(seatsDto.get(i).getSection()).isEqualTo(seats.get(i).getSection());
        }
    }

    @Test
    void testMapToSeats() {
        SeatsDto seatsDto = new SeatsDto();
        seatsDto.setSection(Section.SECTION_A);
        List<SeatsDto> seatsInDto = List.of(seatsDto);

        List<Seat> seats = mapToSeats(seatsInDto);

        assertThat(seats).isNotNull();
        assertThat(seats.size()).isEqualTo(seatsInDto.size());

        // Check if each element in seats has the corresponding section from seatsDto
        for (int i = 0; i < seatsInDto.size(); i++) {
            assertThat(seats.get(i).getSection()).isEqualTo(seatsInDto.get(i).getSection());
        }
    }

    @Test
    void testFilterSeatsBySection() {
        Ticket ticket = new Ticket();
        ticket.setFromLocation("London");
        ticket.setToLocation("France");
        ticket.setPricePaid(25.0);

        Users users = new Users();
        users.setFirstName("Manoj");
        users.setLastName("Prabhakar");
        users.setEmail("m.p@test.com");
        ticket.setUser(users);

        List<Seat> reservedSeats = List.of(new Seat(1L, Section.SECTION_A, ticket),
                new Seat(1L, Section.SECTION_B, ticket));

        // Call the method under test
        List<SectionsDto> sections = filterSeatsBySection(reservedSeats);

        // Assertions
        assertThat(sections).isNotNull(); // Ensure output list is not empty
        assertThat(sections.size()).isEqualTo(reservedSeats.size()); // Verify same number of sections as seats

        // Check each SectionsDto object for expected values
        for (int i = 0; i < sections.size(); i++) {
            SectionsDto sectionDto = sections.get(i);
            Seat seat = reservedSeats.get(i);
            Ticket reservedTicket = seat.getTicket();
            Users user = ticket.getUser();

            assertThat(sectionDto.getFirstName()).isEqualTo(user.getFirstName());
            assertThat(sectionDto.getSeatId()).isEqualTo(seat.getSeatId());
            assertThat(sectionDto.getSection()).isEqualTo(seat.getSection());
        }
    }
}