package com.badkultech.trip_management.Controller;

import com.badkultech.trip_management.dto.TripRequestDto;
import com.badkultech.trip_management.dto.TripResponseDto;
import com.badkultech.trip_management.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) { this.tripService = tripService; }

    @PostMapping
    public ResponseEntity<TripResponseDto> create(@Valid @RequestBody TripRequestDto dto) {
        TripResponseDto created = tripService.createTrip(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<TripResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {
        Page<TripResponseDto> p = tripService.getAllTrips(page, size, sort);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> update(@PathVariable Integer id, @Valid @RequestBody TripRequestDto dto) {
        return ResponseEntity.ok(tripService.updateTrip(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TripResponseDto>> search(@RequestParam String destination,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) String sort) {
        Page<TripResponseDto> p = tripService.searchByDestination(destination, page, size, sort);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TripResponseDto>> filter(@RequestParam String status) {
        List<TripResponseDto> res = tripService.filterByStatus(status);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/daterange")
    public ResponseEntity<List<TripResponseDto>> dateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(tripService.getTripsBetween(start, end));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> summary() {
        return ResponseEntity.ok(tripService.getSummary());
    }
}
