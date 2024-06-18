package com.codebees.reservation.mapper;

import com.codebees.reservation.dto.SeatsDto;
import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.dto.UserDto;
import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Section;
import com.codebees.reservation.entity.Ticket;
import com.codebees.reservation.entity.Users;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codebees.reservation.mapper.TicketsMapper.mapToTicket;
import static com.codebees.reservation.mapper.TicketsMapper.mapToTicketsDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TicketsMapperTest {

    @Test
    void testMapToTicket() {
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setFrom("Chicago");
        ticketsDto.setTo("New York");
        ticketsDto.setPrice(100.0);
        UserDto userDto = new UserDto("Manoj", "Prabhakar", "m.p@t.com");
        ticketsDto.setUsers(userDto);
        SeatsDto seatsDto = new SeatsDto();
        seatsDto.setSection(Section.SECTION_A);
//        ticketsDto.setSeats(List.of(seatsDto));

        Ticket updatedTicket = mapToTicket(ticketsDto, new Ticket());

        assertThat(updatedTicket).isNotNull();
        assertThat(updatedTicket.getFromLocation()).isEqualTo(ticketsDto.getFrom());
        assertThat(updatedTicket.getToLocation()).isEqualTo(ticketsDto.getTo());
        assertThat(updatedTicket.getPricePaid()).isEqualTo(ticketsDto.getPrice());

        assertThat(updatedTicket.getUser().getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(updatedTicket.getUser().getEmail()).isEqualTo(userDto.getEmail());

        assertThat(updatedTicket.getSeat().size()).isGreaterThan(0);
    }

    @Test
    public void testMapToTicketsDto() {
        Ticket ticket = new Ticket();
        ticket.setFromLocation("Chicago");
        ticket.setToLocation("New York");
        ticket.setPricePaid(100.0);
        ticket.setUser(new Users(1L, "Manoj", "Prabhakar", "m.p@tc.com", List.of(ticket)));
//        ticket.setSeat(List.of(new Seat(1L, Section.SECTION_A,ticket)));

        TicketsDto ticketsDto = mapToTicketsDto(ticket, new TicketsDto());

        assertThat(ticketsDto).isNotNull(); // Ensure TicketsDto is returned
        assertThat(ticketsDto.getFrom()).isEqualTo(ticket.getFromLocation());
        assertThat(ticketsDto.getTo()).isEqualTo(ticket.getToLocation());
        assertThat(ticketsDto.getPrice()).isEqualTo(ticket.getPricePaid());

        assertThat(ticketsDto.getUsers().getFirstName()).isEqualTo("Manoj");
        assertThat(ticketsDto.getUsers().getEmail()).isEqualTo("m.p@tc.com");

//        assertThat(ticketsDto.getSeats().get(0).getSection()).isEqualTo(Section.SECTION_A);
    }

}