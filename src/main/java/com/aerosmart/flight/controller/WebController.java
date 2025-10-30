package com.aerosmart.flight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/vols")
    public String vols() {
        return "vols disponibles";
    }

    @GetMapping("/ajouter")
    public String ajouter() {
        return "Ajouter Vol";
    }

    @GetMapping("/details")
    public String details() {
        return "DetailleVol";
    }
}