package com.example.restapi.controllers;

public class DoctorNotFoundException extends RuntimeException {

    DoctorNotFoundException(Long id) {
        super("Could not find Doctor: " + id);
    }
}
