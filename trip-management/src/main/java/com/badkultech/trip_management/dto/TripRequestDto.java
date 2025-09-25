package com.badkultech.trip_management.dto;

import com.badkultech.trip_management.validator.EndDateAfterStartDate;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@EndDateAfterStartDate
public class TripRequestDto {

    @NotBlank(message = "destination must not be blank")
    private static String destination;

    @NotNull(message = "startDate is required")
    private static LocalDate startDate;

    @NotNull(message = "endDate is required")
    private static LocalDate endDate;

    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private static Double price;

    @NotBlank(message = "status is required")
    private static String status;

    public TripRequestDto() {}

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}