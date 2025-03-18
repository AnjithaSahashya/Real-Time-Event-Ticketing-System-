package com.example.ticketingsystembackend.controller;

import com.example.ticketingsystembackend.dto.VendorDTO;
import com.example.ticketingsystembackend.model.Vendor;
import com.example.ticketingsystembackend.service.VendorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public void addVendor(@RequestBody VendorDTO vendor) {
        vendorService.addVendor(vendor.getName());
    }

    @DeleteMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public void removeVendor(@RequestParam int id) {
        vendorService.removeVendor(id);
    }

    @GetMapping("/start")
    @CrossOrigin(origins = "http://localhost:5173")
    public String startVendors() {
        vendorService.startVendors();
        return "Started vendor threads";
    }

    @GetMapping("/stop")
    @CrossOrigin(origins = "http://localhost:5173")
    public String stopVendors() {
        vendorService.stopVendors();
        return "Stopped vendor threads";
    }
}
