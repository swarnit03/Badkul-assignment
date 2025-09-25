package com.badkultech.trip_management.dto;

import java.time.LocalDate;

public class TripResponseDto {
    private Integer id;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
    private String status;

    public TripResponseDto() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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
