package com.nexcodelab.agendaclinicaser.application.gestaoocupacaosalas.service;

import com.nexcodelab.agendaclinicaser.application.agenda.ocupacaohorario.model.repository.OcupacaoHorarioRepository;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.model.DisponibilidadeDiaSemana;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.model.DisponibilidadeHoraria;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.model.DisponibilidadePersona;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.repository.DisponibilidadeRepository;
import com.nexcodelab.agendaclinicaser.core.exceptionhandler.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class RemoverOcupacaoPainelDeSalaUseCase {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final OcupacaoHorarioRepository ocupacaoHorarioRepository;

    public void execute(String idEstagiario, DayOfWeek diaDaSemana, LocalTime horario) {

        ocupacaoHorarioRepository.existTerapeutaEmServicoAposData(idEstagiario, LocalDateTime.now());

        DisponibilidadePersona disponibilidade = disponibilidadeRepository.findByPersonaId(idEstagiario)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidade não encontrada"));

            removerOcupacao(disponibilidade, diaDaSemana, horario);
    }

    private void removerOcupacao(DisponibilidadePersona disponibilidade,
                                 DayOfWeek diaDaSemana,
                                 LocalTime horario) {

        DisponibilidadeDiaSemana dia = disponibilidade.getDiaDaSemana().stream().filter(d -> d.getDiaDaSemana().equals(diaDaSemana)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidade não encontrada para o dia: " + diaDaSemana));

        DisponibilidadeHoraria disponibilidadeHoraria = dia.getHorarios().stream().filter(h -> h.getHorario().equals(horario)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Horário não encontrado: " + horario));


        disponibilidadeHoraria.removerSala();
    }
}
