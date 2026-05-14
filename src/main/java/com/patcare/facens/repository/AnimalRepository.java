package com.patcare.facens.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patcare.facens.entity.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}