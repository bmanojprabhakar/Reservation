package com.codebees.reservation.repository;

import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Section;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findBySection(Section section);

    Optional<Seat> findBySeatId(Long seatId);

    @Query("select s from Seat s where s.section=?1 and s.ticket.ticketId is null")
    List<Seat> findEmptySeatsInASection(Section section);

    List<Seat> findByTicket_TicketId(Long ticketId);

    @Transactional
    void deleteByPassenger_Id(Long id);
}
