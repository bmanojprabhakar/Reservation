package com.codebees.reservation.service;

import com.codebees.reservation.dto.TicketsDto;
import com.codebees.reservation.entity.Ticket;

public interface TicketService {
    Ticket purchaseTicket(TicketsDto ticketRequest);

    TicketsDto generateTicketReceipt(Long pnrNumber);

    void cancelTicket(Long pnrNumber);
}
