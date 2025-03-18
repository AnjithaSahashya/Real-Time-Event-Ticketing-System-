package com.example.ticketingsystembackend.controller;

import com.example.ticketingsystembackend.dto.CustomerDTO;
import com.example.ticketingsystembackend.model.Customer;
import com.example.ticketingsystembackend.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public void addCustomer(@RequestBody CustomerDTO customer) {
        customerService.addCustomer(customer.getName(), customer.getPriority());
    }


    @DeleteMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public void removeCustomer(@RequestParam int id) {
        customerService.removeCustomer(id);
    }

    @GetMapping("/start")
    @CrossOrigin(origins = "http://localhost:5173")
    public String startCustomers() {
        customerService.startCustomers();
        return "Started customer threads";
    }

    @GetMapping("/stop")
    @CrossOrigin(origins = "http://localhost:5173")
    public String stopCustomers() {
        customerService.stopCustomers();
        return "Stopped customer threads";
    }
}
