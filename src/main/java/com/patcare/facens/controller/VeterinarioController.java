package com.patcare.facens.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patcare.facens.entity.Veterinario;
import com.patcare.facens.services.VeterinarioService;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final VeterinarioService service;

    public VeterinarioController(VeterinarioService service) {
        this.service = service;
    }

    @PostMapping
    public Veterinario salvar(@RequestBody Veterinario v) {
        return service.salvar(v);
    }
}
