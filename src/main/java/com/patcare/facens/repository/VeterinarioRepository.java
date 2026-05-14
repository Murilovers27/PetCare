package com.patcare.facens.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.patcare.facens.entity.Veterinario;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
}
