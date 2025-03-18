package com.example.ticketingsystembackend.repository;

import com.example.ticketingsystembackend.model.Ticket;
import com.example.ticketingsystembackend.model.Vendor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ticket> getAllTickets() {
        String sql = "SELECT * FROM tickets";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Ticket ticket = new Ticket(rs.getInt("id"), rs.getString("ticket_number"), rs.getString("status"), rs.getInt("customer_id"), rs.getInt("vendor_id"));
            return ticket;
        });
    }

    public Ticket getTicketById(int id) {
        String sql = "SELECT * FROM tickets WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TicketRepository.TicketRowMapper());
    }

    public int addTicket(String ticketNumber, String status, int vendorId) {
        String sql = "INSERT INTO tickets (ticket_number, status, vendor_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, ticketNumber, status, vendorId);

        String getId = "SELECT last_insert_rowid()";
        return jdbcTemplate.queryForObject(getId, Integer.class);
    }

    public void removeTicket(int id, int customerId) {
        String sql = "UPDATE tickets SET customer_id = ?, status = 'SOLD' WHERE id = ?";
        jdbcTemplate.update(sql, customerId, id);
    }

    // RowMapper to map ResultSet to Ticket object
    private static class TicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket(rs.getInt("id"), rs.getString("ticket_number"), rs.getString("status"), rs.getInt("customer_id"), rs.getInt("vendor_id"));
            return ticket;
        }
    }
}
