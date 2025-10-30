package com.aerosmart.flight;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {

    @GetMapping("/simple")
    @ResponseBody
    public String simple() {
        return "CONTROLEUR DETECTE - CA MARCHE !";
    }
    
    // @GetMapping("/")
    // public String index() {
    //     return "index";
    // }
}