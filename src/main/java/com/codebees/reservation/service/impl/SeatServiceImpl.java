package com.codebees.reservation.service.impl;

import com.codebees.reservation.dto.SectionsDto;
import com.codebees.reservation.entity.Seat;
import com.codebees.reservation.entity.Section;
import com.codebees.reservation.exception.SeatNotFoundException;
import com.codebees.reservation.mapper.SeatMapper;
import com.codebees.reservation.repository.SeatRepository;
import com.codebees.reservation.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService {
    private final String className = this.getClass().getSimpleName();
    private SeatRepository seatRepository;

    /**
     * To retrieve seat details for the given section
     * @param section - enum of section
     * @return Customized DTO which holds list of necessary passenger information
     */
    @Override
    public List<SectionsDto> fetchSeatDetails(Section section) {
        log.debug("Entering {}:fetchSeatDetails()", className);
        List<Seat> seats = seatRepository.findBySection(section);
        log.debug("Exiting {}:fetchSeatDetails()", className);
        return SeatMapper.filterSeatsBySection(seats);
    }

    /**
     * Updates the section of a given seat
     * @param seatId - seat number of input request
     * @param newSection - request change section
     */
    @Override
    public void modifySeat(Long seatId, Section newSection) {
        log.debug("Entering {}:modifySeat()", className);
        Optional<Seat> existingSeat = seatRepository.findBySeatId(seatId);

        if(existingSeat.isEmpty()) {
            throw new SeatNotFoundException("Given Seat ID "+seatId+" is invalid");
        }
        Seat seat = existingSeat.get();
        seat.setSection(newSection);
        seatRepository.save(seat);
        log.debug("Exiting {}:modifySeat()", className);
    }
}
