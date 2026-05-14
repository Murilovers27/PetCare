package com.patcare.facens.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patcare.facens.entity.Animal;
import com.patcare.facens.services.AnimalService;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @PostMapping
    public Animal salvar(@RequestBody Animal a) {
        return service.salvar(a);
    }

    @GetMapping("/{id}")
    public Animal buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
}