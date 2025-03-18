package com.example.ticketingsystembackend.service;

import com.example.ticketingsystembackend.model.Customer;
import com.example.ticketingsystembackend.repository.CustomerRepository;
import com.example.ticketingsystembackend.repository.TicketRepository;
import com.example.ticketingsystembackend.websocket.WebSocketHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final List<Thread> customerThreads = new ArrayList<>();
    private final WebSocketHandler webSocketHandler;

    public CustomerService(CustomerRepository customerRepository, TicketRepository ticketRepository, TicketService ticketService, WebSocketHandler webSocketHandler) {
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.webSocketHandler = webSocketHandler;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    public void addCustomer(String name, int priority) {
        customerRepository.addCustomer(name, priority);
    }

    public void removeCustomer(int id) {
        customerRepository.removeCustomer(id);
    }


    public void startCustomers() {
        List<Customer> customers = customerRepository.getAllCustomers();

        for (int i = 0; i < customers.size(); i++) {
            Thread thread = new Thread(new CustomerRunnable(ticketService.getTicketPool(), customers.get(i).getId(), customerRepository, ticketRepository, ticketService, webSocketHandler));
            customerThreads.add(thread);
            thread.start();
        }
    }

    public void stopCustomers() {
        for (Thread thread : customerThreads) {
            thread.interrupt();
        }
        customerThreads.clear();
    }
}
