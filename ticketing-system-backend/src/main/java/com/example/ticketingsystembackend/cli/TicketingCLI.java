package com.example.ticketingsystembackend.cli;

import com.example.ticketingsystembackend.model.TicketConfig;
import com.example.ticketingsystembackend.service.TicketConfigService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TicketingCLI implements CommandLineRunner {
    private final TicketConfigService configurationService;

    public TicketingCLI(TicketConfigService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Ticketing System Configuration CLI!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Current Configuration");
            System.out.println("2. Update Configuration");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewConfiguration();
                    break;
                case 2:
                    updateConfiguration(scanner);
                    break;
                case 3:
                    System.out.println("Exiting CLI. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewConfiguration() {
        try {
            TicketConfig configuration = configurationService.getConfiguration();
            System.out.println("\nCurrent Configuration:");
            System.out.println("Total Tickets: " + configuration.getTotalTickets());
            System.out.println("Ticket Release Rate: " + configuration.getTicketReleaseRate());
            System.out.println("Customer Retrieval Rate: " + configuration.getCustomerRetrievalRate());
            System.out.println("Maximum Ticket Capacity: " + configuration.getMaxTicketCapacity());
        } catch (RuntimeException e) {
            System.err.println("Error fetching configuration: " + e.getMessage());
        }
    }

    private void updateConfiguration(Scanner scanner) {
        try {
            TicketConfig configuration = new TicketConfig();

            System.out.print("\nEnter Total Tickets: ");
            configuration.setTotalTickets(scanner.nextInt());

            System.out.print("Enter Ticket Release Rate: ");
            configuration.setTicketReleaseRate(scanner.nextInt());

            System.out.print("Enter Customer Retrieval Rate: ");
            configuration.setCustomerRetrievalRate(scanner.nextInt());

            System.out.print("Enter Maximum Ticket Capacity: ");
            configuration.setMaxTicketCapacity(scanner.nextInt());

            configurationService.saveConfiguration(configuration);
            System.out.println("Configuration updated successfully.");
        } catch (RuntimeException e) {
            System.err.println("Error updating configuration: " + e.getMessage());
        }
    }
}
