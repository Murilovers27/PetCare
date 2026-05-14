package com.patcare.facens.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patcare.facens.entity.Consulta;
import com.patcare.facens.repository.ConsultaRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    /**
     * Agenda uma consulta aplicando validações de negócio:
     * - parâmetros obrigatórios
     * - conflito de agenda (mesmo veterinário na mesma data/hora)
     * - compatibilidade entre especialidade do veterinário e espécie do animal
     */
    @Transactional
    public Consulta agendarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new RuntimeException("Consulta inválida");
        }

        if (consulta.getVeterinario() == null || consulta.getAnimal() == null || consulta.getData() == null) {
            throw new RuntimeException("Dados insuficientes para agendamento");
        }

        // REGRA 1 – conflito de agenda
        boolean conflito = repository.existsByVeterinarioAndData(
                consulta.getVeterinario(),
                consulta.getData()
        );

        if (conflito) {
            throw new RuntimeException("Horário já ocupado!");
        }

        // REGRA 2 – especialidade compatível com espécie
        String especialidade = Optional.ofNullable(consulta.getVeterinario().getEspecialidade()).orElse("");
        String especie = Optional.ofNullable(consulta.getAnimal().getEspecie()).orElse("");

        if (!especialidade.trim().equalsIgnoreCase(especie.trim())) {
            throw new RuntimeException("Veterinário não atende essa espécie!");
        }

        return repository.save(consulta);
    }
}