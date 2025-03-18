package com.example.ticketingsystembackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/health")
    @CrossOrigin(origins = "http://localhost:5173")
    @ResponseBody
    // Method
    public ResponseEntity<String> healthCheck()
    {
        return new ResponseEntity<>("Healthy", HttpStatus.OK);
    }
}