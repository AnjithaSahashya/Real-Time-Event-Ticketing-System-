package com.example.ticketingsystembackend.model;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.persistence.*;

@Table("tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status;
    private String ticketNumber;
    private Integer customerId;
    private Integer vendorId;

    public Ticket(int id, String ticketNumber, String status, Integer customerId, Integer vendorId) {
        this.id = id;
        this.status = status;
        this.ticketNumber = ticketNumber;
        this.customerId = customerId;
        this.vendorId = vendorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }
}

