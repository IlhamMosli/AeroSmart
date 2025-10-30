package com.aerosmart.flight;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Classe de test principale pour l'application Flight Service
 * 
 * Tests d'intégration et de contexte Spring Boot
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class FlightServiceApplicationTests {

    /**
     * Test de chargement du contexte Spring Boot
     * Vérifie que l'application démarre correctement
     */
    @Test
    void contextLoads() {
        // Ce test vérifie que le contexte Spring se charge sans erreur
        System.out.println(" Contexte Spring Boot chargé avec succès");
    }

    /**
     * Test de démarrage de l'application
     * Vérifie que tous les beans sont correctement configurés
     */
    @Test
    void applicationStartsSuccessfully() {
        // Test que l'application peut démarrer sans exception
        FlightServiceApplication.main(new String[] {});
        System.out.println(" Application démarrée avec succès");
    }
}