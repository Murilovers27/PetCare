package com.patcare.facens.services;


import org.springframework.stereotype.Service;

import com.patcare.facens.entity.Veterinario;
import com.patcare.facens.repository.VeterinarioRepository;

@Service
public class VeterinarioService {

    private final VeterinarioRepository repository;

    public VeterinarioService(VeterinarioRepository repository) {
        this.repository = repository;
    }

    public Veterinario salvar(Veterinario v) {
        return repository.save(v);
    }
}
