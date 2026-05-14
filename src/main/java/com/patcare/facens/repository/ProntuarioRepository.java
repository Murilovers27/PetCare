package com.patcare.facens.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patcare.facens.entity.Prontuario;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    // histórico do animal
    List<Prontuario> findByAnimalId(Long animalId);
}
