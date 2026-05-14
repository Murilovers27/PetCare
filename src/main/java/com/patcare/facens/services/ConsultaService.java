package com.patcare.facens.services;

import org.springframework.stereotype.Service;

import com.patcare.facens.entity.Consulta;
import com.patcare.facens.repository.ConsultaRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public Consulta agendarConsulta(Consulta consulta) {

        // ✅ REGRA 1 – conflito de agenda
        boolean conflito = repository.existsByVeterinarioAndData(
                consulta.getVeterinario(),
                consulta.getData()
        );

        if (conflito) {
            throw new RuntimeException("Horário já ocupado!");
        }

        // ✅ REGRA 2 – especialidade
        if (!consulta.getVeterinario().getEspecialidade()
                .equalsIgnoreCase(consulta.getAnimal().getEspecie())) {

            throw new RuntimeException("Veterinário não atende essa espécie!");
        }

        return repository.save(consulta);
    }
}