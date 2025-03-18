CREATE TABLE IF NOT EXISTS tickets (
                                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       ticket_number TEXT NOT NULL,
                                       status TEXT NOT NULL, -- "AVAILABLE", "SOLD"
                                       customer_id INTEGER,
                                       vendor_id INTEGER
);

CREATE TABLE IF NOT EXISTS vendors (
                                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS customers (
                                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                                         name TEXT NOT NULL,
                                         priority INTEGER DEFAULT 0
);
