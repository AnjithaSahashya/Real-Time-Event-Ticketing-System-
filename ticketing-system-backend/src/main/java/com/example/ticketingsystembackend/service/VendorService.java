package com.example.ticketingsystembackend.service;

import com.example.ticketingsystembackend.model.TicketConfig;
import com.example.ticketingsystembackend.model.Vendor;
import com.example.ticketingsystembackend.repository.TicketRepository;
import com.example.ticketingsystembackend.repository.VendorRepository;
import com.example.ticketingsystembackend.websocket.WebSocketHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final WebSocketHandler webSocketHandler;

    public VendorService(VendorRepository vendorRepository, TicketRepository ticketRepository, TicketService ticketService, WebSocketHandler webSocketHandler) {
        this.vendorRepository = vendorRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.webSocketHandler = webSocketHandler;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.getAllVendors();
    }
    public Vendor getVendorById(int id) {
        return vendorRepository.getVendorById(id);
    }
    public void addVendor(String name) {
        vendorRepository.addVendor(name);
    }
    public void removeVendor(int id) {
        vendorRepository.removeVendor(id);
    }

    public void startVendors() {
        List<Vendor> vendors = vendorRepository.getAllVendors();

        for (int i = 0; i < vendors.size(); i++) {
            Thread thread = new Thread(new VendorRunnable(ticketService.getTicketPool(), new TicketConfigService().getConfiguration().getTicketReleaseRate(), vendors.get(i).getId(), vendorRepository, ticketRepository, ticketService, webSocketHandler));
            vendorThreads.add(thread);
            thread.start();
        }
    }

    public void stopVendors() {
        for (Thread thread : vendorThreads) {
            thread.interrupt();
        }
        vendorThreads.clear();
    }

}
