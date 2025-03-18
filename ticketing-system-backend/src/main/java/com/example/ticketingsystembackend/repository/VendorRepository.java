package com.example.ticketingsystembackend.repository;

import com.example.ticketingsystembackend.model.Vendor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VendorRepository {
    private final JdbcTemplate jdbcTemplate;

    public VendorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Vendor> getAllVendors() {
        String sql = "SELECT * FROM vendors";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vendor vendor = new Vendor(rs.getInt("id"), rs.getString("name"));
            return vendor;
        });
    }

    public Vendor getVendorById(int id) {
        String sql = "SELECT * FROM vendors WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new VendorRowMapper());
    }

    public void addVendor(String name) {
        String sql = "INSERT INTO vendors (name) VALUES (?)";
        jdbcTemplate.update(sql, name);
    }

    public void removeVendor(int id) {
        String sql = "DELETE FROM vendors WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    // RowMapper to map ResultSet to Vendor object
    private static class VendorRowMapper implements RowMapper<Vendor> {
        @Override
        public Vendor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vendor vendor = new Vendor(rs.getInt("id"), rs.getString("name"));
            return vendor;
        }
    }
}
