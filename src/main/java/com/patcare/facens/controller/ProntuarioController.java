package com.patcare.facens.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patcare.facens.entity.Prontuario;
import com.patcare.facens.services.ProntuarioService;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    private final ProntuarioService service;

    public ProntuarioController(ProntuarioService service) {
        this.service = service;
    }

    @PostMapping
    public Prontuario salvar(@RequestBody Prontuario p) {
        return service.salvar(p);
    }

    @GetMapping("/animal/{id}")
    public List<Prontuario> listar(@PathVariable Long id) {
        return service.listarPorAnimal(id);
    }
}