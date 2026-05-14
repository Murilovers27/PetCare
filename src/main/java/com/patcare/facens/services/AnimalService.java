package com.patcare.facens.services;


import org.springframework.stereotype.Service;

import com.patcare.facens.entity.Animal;
import com.patcare.facens.repository.AnimalRepository;

@Service
public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public Animal salvar(Animal a) {
        return repository.save(a);
    }

    public Animal buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }
}