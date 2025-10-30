package com.aerosmart.flight.repository;

import com.aerosmart.flight.model.Flight;  // ← CORRIGER ICI
import com.aerosmart.flight.model.FlightStatus;  // ← CORRIGER ICI
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    Optional<Flight> findByFlightNumber(String flightNumber);
    
    List<Flight> findByOriginAndDestination(String origin, String destination);
    
    List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(
            String origin, String destination, LocalDateTime startDate, LocalDateTime endDate);
    
    List<Flight> findByStatus(FlightStatus status);
    
    List<Flight> findByAvailableSeatsGreaterThan(int seats);
    
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination AND DATE(f.departureTime) = :date")
    List<Flight> findByOriginDestinationAndDate(
            @Param("origin") String origin, 
            @Param("destination") String destination, 
            @Param("date") LocalDate date);
    
    @Query("SELECT f FROM Flight f WHERE f.departureTime BETWEEN :start AND :end")
    List<Flight> findFlightsInPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    boolean existsByFlightNumber(String flightNumber);
}