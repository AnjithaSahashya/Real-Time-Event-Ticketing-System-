package com.example.ticketingsystembackend.service;

import com.example.ticketingsystembackend.model.TicketConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class TicketConfigService {
    private final String CONFIG_FILE = "config.json";

    /**
     * Save the configuration to a JSON file.
     * @param configuration Configuration object to save.
     */
    public void saveConfiguration(TicketConfig configuration) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(CONFIG_FILE), configuration);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration to file", e);
        }
    }

    /**
     * Read the configuration from a JSON file.
     * @return Configuration object read from the file.
     */
    public TicketConfig getConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(CONFIG_FILE);
            if (file.exists()) {
                return objectMapper.readValue(file, TicketConfig.class);
            } else {
                return new TicketConfig(); // Return a default configuration if file doesn't exist
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read configuration from file", e);
        }
    }
}
