package com.example.ticketingsystembackend.controller;
import com.example.ticketingsystembackend.model.TicketConfig;
import com.example.ticketingsystembackend.service.TicketConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class TicketConfigController {
    private final TicketConfigService configurationService;

    public TicketConfigController(TicketConfigService configurationService) {
        this.configurationService = configurationService;
    }

    /**
     * Endpoint to save the configuration.
     * @param configuration Configuration details from the frontend.
     * @return Response message.
     */
    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> saveConfiguration(@RequestBody TicketConfig configuration) {
        configurationService.saveConfiguration(configuration);
        return ResponseEntity.ok("Configuration saved successfully");
    }

    /**
     * Endpoint to fetch the current configuration.
     * @return The current configuration.
     */
    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<TicketConfig> getConfiguration() {
        TicketConfig configuration = configurationService.getConfiguration();
        return ResponseEntity.ok(configuration);
    }
}
