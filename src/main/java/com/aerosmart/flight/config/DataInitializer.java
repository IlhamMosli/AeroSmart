package com.aerosmart.flight.config;

import com.aerosmart.flight.model.Flight;
import com.aerosmart.flight.model.FlightStatus;
import com.aerosmart.flight.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initData(FlightRepository flightRepository) {
        return args -> {
            if (flightRepository.count() == 0) {
                // Vol 1 - Utilisation du constructeur avec paramètres
                Flight flight1 = new Flight(
                    "AF345", 
                    "Casablanca", 
                    "Agadir",
                    LocalDateTime.now().plusDays(1).withHour(12).withMinute(45),
                    LocalDateTime.now().plusDays(1).withHour(14).withMinute(30),
                    150,
                    3000.0
                );
                flight1.setGateNumber("B31");
                
                // Vol 2 - Utilisation du constructeur avec paramètres
                Flight flight2 = new Flight(
                    "AF349", 
                    "Tanger", 
                    "Agadir",
                    LocalDateTime.now().plusDays(2).withHour(9).withMinute(30),
                    LocalDateTime.now().plusDays(2).withHour(11).withMinute(0),
                    180,
                    2500.0
                );
                flight2.setGateNumber("A15");
                
                // Vol 3 - Vol retardé
                Flight flight3 = new Flight(
                    "AT202",
                    "Marrakech",
                    "Rabat",
                    LocalDateTime.now().plusHours(2),
                    LocalDateTime.now().plusHours(3).withMinute(30),
                    120,
                    1800.0
                );
                flight3.setStatus(FlightStatus.DELAYED);
                flight3.setGateNumber("C22");
                
                flightRepository.save(flight1);
                flightRepository.save(flight2);
                flightRepository.save(flight3);
                
                System.out.println("✅ Données de test initialisées avec " + flightRepository.count() + " vols!");
            } else {
                System.out.println("✅ Base de données contient déjà " + flightRepository.count() + " vols");
            }
        };
    }
}