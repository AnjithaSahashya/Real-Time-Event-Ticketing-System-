package com.example.ticketingsystembackend.service;

import com.example.ticketingsystembackend.model.Ticket;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class TicketPool {
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());
    private final List<Ticket> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;
    private int addedTicketCount = 0;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket(String ticketNumber, int vendorId, TicketService ticketService) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            logger.info("TicketPool is full. Vendor is waiting...");
            wait();
        }
        int ticketId = ticketService.addTicket(ticketNumber, vendorId);
        Ticket ticket = ticketService.getTicketById(ticketId);
        tickets.add(ticket);
        logger.info("Added ticket: " + ticket.getTicketNumber());
        notifyAll();
    }

    public synchronized Ticket removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            logger.info("TicketPool is empty. Customer is waiting...");
            wait();
        }
        Ticket ticket = tickets.remove(0);
        logger.info("Ticket purchased: " + ticket.getTicketNumber());
        notifyAll();
        return ticket;
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }

    public synchronized int getAddedTicketCount() {
        return addedTicketCount;
    }

    public synchronized void setAddedTicketCount(int addedTicketCount) {
        this.addedTicketCount = addedTicketCount;
    }

}
