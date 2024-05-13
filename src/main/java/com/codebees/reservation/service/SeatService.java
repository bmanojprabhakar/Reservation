package com.codebees.reservation.service;

import com.codebees.reservation.dto.SectionsDto;
import com.codebees.reservation.entity.Section;

import java.util.List;

public interface SeatService {
    List<SectionsDto> fetchSeatDetails(Section section);
    void modifySeat(Long seatId, Section newSection);
}
