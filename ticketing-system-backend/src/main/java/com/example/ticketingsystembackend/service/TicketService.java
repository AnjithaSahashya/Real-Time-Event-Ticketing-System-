package com.example.ticketingsystembackend.service;

import com.example.ticketingsystembackend.model.Ticket;
import com.example.ticketingsystembackend.repository.TicketRepository;
import com.example.ticketingsystembackend.websocket.WebSocketHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketPool ticketPool;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketPool = new TicketPool(new TicketConfigService().getConfiguration().getMaxTicketCapacity());
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }
    public Ticket getTicketById(int id) {
        return ticketRepository.getTicketById(id);
    }

    public int addTicket(String ticketNumber, int vendorId) {
        return ticketRepository.addTicket(ticketNumber, "AVAILABLE", vendorId);
    }

    public void removeTicket(int id, int customerId) {
        ticketRepository.removeTicket(id, customerId);
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }
}
