package com.aerosmart.flight.service;

import com.aerosmart.flight.model.Flight;
import com.aerosmart.flight.model.FlightStatus;
import com.aerosmart.flight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    // Récupérer tous les vols
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Récupérer un vol par ID
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    // Récupérer un vol par numéro
    public Optional<Flight> getFlightByNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    // Récupérer les vols par origine et destination
    public List<Flight> getFlightsByRoute(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    // Récupérer les vols par statut
    public List<Flight> getFlightsByStatus(FlightStatus status) {
        return flightRepository.findByStatus(status);
    }

    // Récupérer les vols avec sièges disponibles
    public List<Flight> getAvailableFlights(int minSeats) {
        return flightRepository.findByAvailableSeatsGreaterThan(minSeats - 1);
    }

    // Créer un nouveau vol
    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    // Mettre à jour un vol
    public Flight updateFlight(Long id, Flight flightDetails) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flight.setFlightNumber(flightDetails.getFlightNumber());
                    flight.setOrigin(flightDetails.getOrigin());
                    flight.setDestination(flightDetails.getDestination());
                    flight.setDepartureTime(flightDetails.getDepartureTime());
                    flight.setArrivalTime(flightDetails.getArrivalTime());
                    flight.setTotalSeats(flightDetails.getTotalSeats());
                    flight.setAvailableSeats(flightDetails.getAvailableSeats());
                    flight.setStatus(flightDetails.getStatus());
                    flight.setPrice(flightDetails.getPrice());
                    flight.setGateNumber(flightDetails.getGateNumber());
                    return flightRepository.save(flight);
                })
                .orElse(null);
    }

    // Supprimer un vol
    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Mettre à jour le statut d'un vol
    public boolean updateFlightStatus(Long flightId, FlightStatus newStatus) {
        return flightRepository.findById(flightId)
                .map(flight -> {
                    flight.setStatus(newStatus);
                    flightRepository.save(flight);
                    return true;
                })
                .orElse(false);
    }
}
