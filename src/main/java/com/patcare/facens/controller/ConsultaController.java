package com.patcare.facens.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patcare.facens.entity.Consulta;
import com.patcare.facens.services.ConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public Consulta agendar(@RequestBody Consulta c) {
        return service.agendarConsulta(c);
    }
}