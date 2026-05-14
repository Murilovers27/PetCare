package com.patcare.facens.repository;


import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patcare.facens.entity.Consulta;
import com.patcare.facens.entity.Veterinario;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // 🔥 REGRA: verificar conflito de agenda
    boolean existsByVeterinarioAndData(Veterinario veterinario, LocalDateTime data);
}
