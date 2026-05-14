package com.patcare.facens.services;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.patcare.facens.entity.Animal;
import com.patcare.facens.entity.Consulta;
import com.patcare.facens.entity.Veterinario;
import com.patcare.facens.repository.ConsultaRepository;

public class ConsultaServiceTest {

    private final ConsultaRepository repository = mock(ConsultaRepository.class);
    private final ConsultaService service = new ConsultaService(repository);

    // ✅ TESTE: deve agendar corretamente
    @Test
    void deveAgendarConsultaComSucesso() {

        Veterinario vet = new Veterinario();
        vet.setEspecialidade("cachorro");

        Animal animal = new Animal();
        animal.setEspecie("cachorro");

        Consulta consulta = new Consulta();
        consulta.setVeterinario(vet);
        consulta.setAnimal(animal);
        consulta.setData(LocalDateTime.now());

        when(repository.existsByVeterinarioAndData(any(), any()))
                .thenReturn(false);

        when(repository.save(any()))
                .thenReturn(consulta);

        Consulta resultado = service.agendarConsulta(consulta);

        assertNotNull(resultado);
        verify(repository, times(1)).save(consulta);
    }

    // ✅ TESTE: conflito de agenda
    @Test
    void naoDevePermitirHorarioDuplicado() {

        Veterinario vet = new Veterinario();
        Animal animal = new Animal();

        Consulta consulta = new Consulta();
        consulta.setVeterinario(vet);
        consulta.setAnimal(animal);
        consulta.setData(LocalDateTime.now());

        when(repository.existsByVeterinarioAndData(any(), any()))
                .thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.agendarConsulta(consulta);
        });

        assertEquals("Horário já ocupado!", ex.getMessage());
    }

    // ✅ TESTE: especialidade inválida
    @Test
    void naoDevePermitirEspecialidadeInvalida() {

        Veterinario vet = new Veterinario();
        vet.setEspecialidade("gato");

        Animal animal = new Animal();
        animal.setEspecie("cachorro");

        Consulta consulta = new Consulta();
        consulta.setVeterinario(vet);
        consulta.setAnimal(animal);
        consulta.setData(LocalDateTime.now());

        when(repository.existsByVeterinarioAndData(any(), any()))
                .thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.agendarConsulta(consulta);
        });

        assertEquals("Veterinário não atende essa espécie!", ex.getMessage());
    }
}