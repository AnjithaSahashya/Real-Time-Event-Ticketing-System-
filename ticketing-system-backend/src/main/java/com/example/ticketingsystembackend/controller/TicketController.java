package com.example.ticketingsystembackend.controller;

import com.example.ticketingsystembackend.model.Ticket;
import com.example.ticketingsystembackend.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public void addTicket(@RequestBody String ticketNumber, @RequestBody int vendorId) {
        ticketService.addTicket(ticketNumber, vendorId);
    }
}
