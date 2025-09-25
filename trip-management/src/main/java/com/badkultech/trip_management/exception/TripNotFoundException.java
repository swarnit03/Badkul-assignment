package com.badkultech.trip_management.exception;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(String message) { super(message); }
}
