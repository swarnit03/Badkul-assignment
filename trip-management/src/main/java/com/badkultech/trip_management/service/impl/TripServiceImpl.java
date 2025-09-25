package com.badkultech.trip_management.service.impl;

import com.badkultech.trip_management.dto.TripRequestDto;
import com.badkultech.trip_management.dto.TripResponseDto;
import com.badkultech.trip_management.entity.Status;
import com.badkultech.trip_management.entity.Trip;
import com.badkultech.trip_management.exception.TripNotFoundException;
import com.badkultech.trip_management.repository.TripRepository;
import com.badkultech.trip_management.service.TripService;
import com.badkultech.trip_management.util.TripMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public TripResponseDto createTrip(TripRequestDto dto) {
        Trip trip = TripMapper.toEntity(dto);
        Trip saved = tripRepository.save(trip);
        return TripMapper.toResponseDto(saved);
    }

    @Override
    public Page<TripResponseDto> getAllTrips(int page, int size, String sort) {
        Pageable pageable = buildPageable(page, size, sort);
        Page<Trip> p = tripRepository.findAll(pageable);
        List<TripResponseDto> list = p.stream().map(TripMapper::toResponseDto).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, p.getTotalElements());
    }

    @Override
    public TripResponseDto getTripById(Integer id) {
        Trip t = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + id));
        return TripMapper.toResponseDto(t);
    }

    @Override
    public TripResponseDto updateTrip(Integer id, TripRequestDto dto) {
        Trip existing = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + id));
        existing.setDestination(dto.getDestination());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setPrice(dto.getPrice());
        existing.setStatus(parseStatus(dto.getStatus()));
        Trip updated = tripRepository.save(existing);
        return TripMapper.toResponseDto(updated);
    }

    @Override
    public void deleteTrip(Integer id) {
        if (!tripRepository.existsById(id)) {
            throw new TripNotFoundException("Trip not found with id: " + id);
        }
        tripRepository.deleteById(id);
    }

    @Override
    public Page<TripResponseDto> searchByDestination(String destination, int page, int size, String sort) {
        Pageable pageable = buildPageable(page, size, sort);
        Page<Trip> p = tripRepository.findByDestinationContainingIgnoreCase(destination, pageable);
        List<TripResponseDto> list = p.stream().map(TripMapper::toResponseDto).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, p.getTotalElements());
    }

    @Override
    public List<TripResponseDto> filterByStatus(String status) {
        Status s = parseStatus(status);
        List<Trip> trips = tripRepository.findByStatus(s);
        return trips.stream().map(TripMapper::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<TripResponseDto> getTripsBetween(LocalDate start, LocalDate end) {
        List<Trip> trips = tripRepository.findByStartDateBetween(start, end);
        return trips.stream().map(TripMapper::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getSummary() {
        List<Trip> all = tripRepository.findAll();
        DoubleSummaryStatistics stats = all.stream()
                .filter(t -> t.getPrice() != null)
                .mapToDouble(Trip::getPrice)
                .summaryStatistics();

        Map<String, Object> m = new HashMap<>();
        m.put("totalTrips", all.size());
        m.put("minPrice", stats.getCount() > 0 ? stats.getMin() : null);
        m.put("maxPrice", stats.getCount() > 0 ? stats.getMax() : null);
        m.put("avgPrice", stats.getCount() > 0 ? stats.getAverage() : null);

        Map<String, Long> byStatus = new HashMap<>();
        for (Trip t : all) {
            String st = t.getStatus() != null ? t.getStatus().name() : "UNKNOWN";
            byStatus.put(st, byStatus.getOrDefault(st, 0L) + 1L);
        }
        m.put("countByStatus", byStatus);
        return m;
    }

    private Pageable buildPageable(int page, int size, String sort) {
        if (sort == null || sort.isBlank()) return PageRequest.of(page, size);
        String[] parts = sort.split(",");
        String prop = parts[0];
        Sort.Direction dir = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(dir, prop));
    }

    private Status parseStatus(String status) {
        if (status == null) {
            return Status.PLANNED;
        }
        try {
            return Status.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            // default or throw - here we throw to inform client
            throw new IllegalArgumentException("Invalid status. Allowed values: PLANNED, ONGOING, COMPLETED, CANCELLED");
        }
    }
}
