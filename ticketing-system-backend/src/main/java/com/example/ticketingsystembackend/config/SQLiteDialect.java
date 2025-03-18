package com.example.ticketingsystembackend.config;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.MySqlDialect;

public class SQLiteDialect extends MySqlDialect {
    public static final SQLiteDialect INSTANCE = new SQLiteDialect();

    private SQLiteDialect() {}

    public static Dialect getInstance() {
        return INSTANCE;
    }
}
