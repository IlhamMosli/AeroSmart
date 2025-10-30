package com.aerosmart.flight.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "flight_number", unique = true, nullable = false, length = 10)
    @NotBlank(message = "Le numéro de vol est obligatoire")
    @Pattern(regexp = "^[A-Z]{2}\\d{3,4}$", message = "Format de vol invalide (ex: AF123)")
    private String flightNumber;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "L'origine est obligatoire")
    private String origin;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "La destination est obligatoire")
    private String destination;
    
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;
    
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;
    
    @Column(name = "total_seats", nullable = false)
    @Min(value = 1, message = "Le nombre total de sièges doit être au moins 1")
    private Integer totalSeats;
    
    @Column(name = "available_seats", nullable = false)
    @Min(value = 0, message = "Les sièges disponibles ne peuvent pas être négatifs")
    private Integer availableSeats;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FlightStatus status;
    
    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Le prix doit être positif")
    private Double price;
    
    @Column(name = "gate_number", length = 10)
    private String gateNumber;
    
    @Column(name = "boarding_start_time")
    private LocalDateTime boardingStartTime;
    
    @Column(name = "boarding_end_time")
    private LocalDateTime boardingEndTime;
    
    @ElementCollection
    @CollectionTable(name = "flight_booked_seats", joinColumns = @JoinColumn(name = "flight_id"))
    @Column(name = "seat_number")
    private List<String> bookedSeats = new ArrayList<>();
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructeurs
    public Flight() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Flight(String flightNumber, String origin, String destination, 
                  LocalDateTime departureTime, LocalDateTime arrivalTime,
                  Integer totalSeats, Double price) {
        this();
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.price = price;
        this.status = FlightStatus.SCHEDULED;
    }
    
    // Pre-update callback
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Méthode utilitaire pour réserver un siège
    public boolean bookSeat(String seatNumber) {
        if (availableSeats > 0 && !bookedSeats.contains(seatNumber)) {
            bookedSeats.add(seatNumber);
            availableSeats--;
            return true;
        }
        return false;
    }
    
    // Méthode utilitaire pour libérer un siège
    public boolean releaseSeat(String seatNumber) {
        if (bookedSeats.contains(seatNumber)) {
            bookedSeats.remove(seatNumber);
            availableSeats++;
            return true;
        }
        return false;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { 
        this.totalSeats = totalSeats; 
        if (this.availableSeats == null || this.availableSeats > totalSeats) {
            this.availableSeats = totalSeats;
        }
    }
    
    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { 
        if (availableSeats > totalSeats) {
            throw new IllegalArgumentException("Les sièges disponibles ne peuvent pas dépasser le total");
        }
        this.availableSeats = availableSeats; 
    }
    
    public FlightStatus getStatus() { return status; }
    public void setStatus(FlightStatus status) { this.status = status; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getGateNumber() { return gateNumber; }
    public void setGateNumber(String gateNumber) { this.gateNumber = gateNumber; }
    
    public LocalDateTime getBoardingStartTime() { return boardingStartTime; }
    public void setBoardingStartTime(LocalDateTime boardingStartTime) { this.boardingStartTime = boardingStartTime; }
    
    public LocalDateTime getBoardingEndTime() { return boardingEndTime; }
    public void setBoardingEndTime(LocalDateTime boardingEndTime) { this.boardingEndTime = boardingEndTime; }
    
    public List<String> getBookedSeats() { return bookedSeats; }
    public void setBookedSeats(List<String> bookedSeats) { this.bookedSeats = bookedSeats; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}