package com.example.ticketingsystembackend.service;

import com.example.ticketingsystembackend.model.Customer;
import com.example.ticketingsystembackend.model.Ticket;
import com.example.ticketingsystembackend.repository.CustomerRepository;
import com.example.ticketingsystembackend.repository.TicketRepository;
import com.example.ticketingsystembackend.websocket.WebSocketHandler;

import java.util.logging.Logger;

public class CustomerRunnable implements Runnable {
    private static final Logger logger = Logger.getLogger(CustomerRunnable.class.getName());
    private final TicketPool ticketPool;
    private final int customerId;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final int customerRetrievalRate = new TicketConfigService().getConfiguration().getCustomerRetrievalRate();
    private final WebSocketHandler webSocketHandler;

    public CustomerRunnable(TicketPool ticketPool, int customerId, CustomerRepository customerRepository, TicketRepository ticketRepository, TicketService ticketService, WebSocketHandler webSocketHandler) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void run() {
            for (int i = 1; i <= customerRetrievalRate; i++) {
                try {
                    Ticket ticket = ticketPool.removeTicket();
                    Customer customer = new CustomerService(customerRepository, ticketRepository, ticketService, webSocketHandler).getCustomerById(customerId);
                    ticketService.removeTicket(ticket.getId(), customer.getId());
                    logger.info(customer.getName() + " purchased ticket: " + ticket.getTicketNumber());
                    webSocketHandler.broadcastMessage("ticketBuying",  customer.getName() + " Purchased: " + ticket.getTicketNumber());
                    Thread.sleep(1000); // Simulating delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.severe("Customer interrupted: " + e.getMessage());
                    break;
                }
            }
    }
}