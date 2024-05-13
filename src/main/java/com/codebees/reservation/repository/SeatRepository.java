package com.codebees.reservation.repository;

import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findBySection(Section section);

    Optional<Seat> findBySeatId(Long seatId);
}
