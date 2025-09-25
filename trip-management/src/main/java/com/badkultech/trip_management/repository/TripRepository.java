package com.badkultech.trip_management.repository;

import com.badkultech.trip_management.entity.Status;
import com.badkultech.trip_management.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    Page<Trip> findByDestinationContainingIgnoreCase(String destination, Pageable pageable);

    List<Trip> findByStatus(Status status);

    List<Trip> findByStartDateBetween(LocalDate start, LocalDate end);
}
