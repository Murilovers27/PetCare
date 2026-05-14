package com.patcare.facens.services;


import org.springframework.stereotype.Service;

import com.patcare.facens.entity.Tutor;
import com.patcare.facens.repository.TutorRepository;

@Service
public class TutorService {

    private final TutorRepository repository;

    public TutorService(TutorRepository repository) {
        this.repository = repository;
    }

    public Tutor salvar(Tutor t) {
        return repository.save(t);
    }
}