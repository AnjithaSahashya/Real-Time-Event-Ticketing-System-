package com.example.ticketingsystembackend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;

@Configuration
@EnableJdbcRepositories
public class JdbcConfig {

    @Bean
    public Dialect jdbcDialect() {
        return SQLiteDialect.getInstance(); // Use the custom SQLite dialect
    }
}
