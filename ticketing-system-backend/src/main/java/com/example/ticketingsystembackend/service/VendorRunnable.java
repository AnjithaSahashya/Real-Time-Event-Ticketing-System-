package com.example.ticketingsystembackend.service;

import com.example.ticketingsystembackend.model.Vendor;
import com.example.ticketingsystembackend.repository.TicketRepository;
import com.example.ticketingsystembackend.repository.VendorRepository;
import com.example.ticketingsystembackend.websocket.WebSocketHandler;

import java.util.logging.Logger;

public class VendorRunnable implements Runnable {
    private static final Logger logger = Logger.getLogger(VendorRunnable.class.getName());
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int vendorId;
    private final VendorRepository vendorRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final int totalTicketCount = new TicketConfigService().getConfiguration().getTotalTickets();
    private final WebSocketHandler webSocketHandler;

    public VendorRunnable(TicketPool ticketPool, int totalTickets, int vendorId, VendorRepository vendorRepository, TicketRepository ticketRepository, TicketService ticketService, WebSocketHandler webSocketHandler) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.vendorId = vendorId;
        this.vendorRepository = vendorRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void run() {
        Vendor vendor = new VendorService(vendorRepository, ticketRepository, ticketService, webSocketHandler).getVendorById(vendorId);
        for (int i = 1; i <= totalTickets; i++) {
            if (ticketPool.getAddedTicketCount() < totalTicketCount) {
                try {
                    String ticketNumber = vendor.getId() + "-Ticket-" + i;
                    ticketPool.addTicket(ticketNumber, vendorId, ticketService);
                    ticketPool.setAddedTicketCount(ticketPool.getAddedTicketCount() + 1);
                    logger.info(vendor.getName() + " added ticket: " + ticketNumber);
                    webSocketHandler .broadcastMessage("ticketSelling",vendor.getName() + " released Ticket: " + ticketNumber);
                    Thread.sleep(500); // Simulating delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.severe(vendor.getName() + " vendor interrupted: " + e.getMessage());
                }
            } else {
                logger.severe("Total ticket count limit reached: " + totalTicketCount);
                break; // Exit the loop when the condition is not met
            }
        }
        logger.info(vendor.getName() + " has finished adding tickets.");
    }
}
