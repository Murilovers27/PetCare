package com.patcare.facens.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.patcare.facens.entity.Prontuario;
import com.patcare.facens.repository.ProntuarioRepository;

@Service
public class ProntuarioService {

    private final ProntuarioRepository repository;

    public ProntuarioService(ProntuarioRepository repository) {
        this.repository = repository;
    }

    public Prontuario salvar(Prontuario p) {
        return repository.save(p);
    }

    public List<Prontuario> listarPorAnimal(Long animalId) {
        return repository.findByAnimalId(animalId);
    }
}