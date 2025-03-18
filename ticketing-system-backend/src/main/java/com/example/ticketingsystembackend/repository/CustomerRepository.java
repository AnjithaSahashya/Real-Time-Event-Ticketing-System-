package com.example.ticketingsystembackend.repository;

import com.example.ticketingsystembackend.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getInt("priority"));
            return customer;
        });
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerRepository.CustomerRowMapper());
    }

    public void addCustomer(String name,int priority) {
        String sql = "INSERT INTO customers (name, priority) VALUES (?, ?)";
        jdbcTemplate.update(sql, name);
    }

    public void removeCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // RowMapper to map ResultSet to Customer object
    private static class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getInt("priority"));
            return customer;
        }
    }
}
