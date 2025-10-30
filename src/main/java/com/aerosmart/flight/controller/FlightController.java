package com.aerosmart.flight.controller;

import com.aerosmart.flight.model.Flight;
import com.aerosmart.flight.model.FlightStatus;
import com.aerosmart.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // GET tous les vols
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    // GET vol par ID
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // GET vol par numéro
    @GetMapping("/number/{flightNumber}")
    public ResponseEntity<Flight> getFlightByNumber(@PathVariable String flightNumber) {
        Optional<Flight> flight = flightService.getFlightByNumber(flightNumber);
        return flight.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // GET vols par route
    @GetMapping("/route")
    public List<Flight> getFlightsByRoute(
            @RequestParam String origin,
            @RequestParam String destination) {
        return flightService.getFlightsByRoute(origin, destination);
    }

    // GET vols par statut
    @GetMapping("/status/{status}")
    public List<Flight> getFlightsByStatus(@PathVariable FlightStatus status) {
        return flightService.getFlightsByStatus(status);
    }

    // GET vols disponibles
    @GetMapping("/available")
    public List<Flight> getAvailableFlights(@RequestParam(defaultValue = "1") int minSeats) {
        return flightService.getAvailableFlights(minSeats);
    }

    // POST créer un vol
    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    // PUT mettre à jour un vol
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(
            @PathVariable Long id,
            @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return updatedFlight != null ? 
               ResponseEntity.ok(updatedFlight) : 
               ResponseEntity.notFound().build();
    }

    // DELETE supprimer un vol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        boolean deleted = flightService.deleteFlight(id);
        return deleted ? 
               ResponseEntity.ok().build() : 
               ResponseEntity.notFound().build();
    }

    // PUT mettre à jour le statut d'un vol
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateFlightStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status) {
        boolean updated = flightService.updateFlightStatus(id, status);
        return updated ? 
               ResponseEntity.ok().build() : 
               ResponseEntity.notFound().build();
    }
}