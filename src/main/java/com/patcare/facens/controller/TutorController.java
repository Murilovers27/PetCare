package com.patcare.facens.controller;


import com.patcare.facens.entity.Tutor;
import com.patcare.facens.services.TutorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    private final TutorService service;

    public TutorController(TutorService service) {
        this.service = service;
    }

    @PostMapping
    public Tutor salvar(@RequestBody Tutor t) {
        return service.salvar(t);
    }
}