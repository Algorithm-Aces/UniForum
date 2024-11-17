package com.nexcodelab.agendaclinicaser.application.gestaoocupacaosalas.service;

import com.nexcodelab.agendaclinicaser.application.atendimento.model.Atendimento;
import com.nexcodelab.agendaclinicaser.application.atendimento.repository.AtendimentoRepository;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.repository.DisponibilidadeRepository;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.vo.DisponibilidadePainelDeSalaVO;
import com.nexcodelab.agendaclinicaser.application.gestaoocupacaosalas.dto.response.PainelDeSalasResponse;
import com.nexcodelab.agendaclinicaser.application.sala.repository.SalaRepository;
import com.nexcodelab.agendaclinicaser.application.sala.vo.ISalaResumidaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PainelDeSalasUseCase {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final SalaRepository salaRepository;

    List<PainelDeSalasResponse> response = new ArrayList<>();

    public List<PainelDeSalasResponse> execute(LocalDate dataReferencia)  {
        List<ISalaResumidaVO> salas = salaRepository.findAllSalaResumida();
        montarDiasDaSemana(salas);


        LocalDateTime dataInicio =  dataReferencia.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
        LocalDateTime dataFim =  dataReferencia.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).atTime(LocalTime.MAX);

        Map<DayOfWeek, List<Atendimento>> atendimentosPorDiaDaSemana = atendimentoRepository.findAtendimentosNoPeriodo(dataInicio, dataFim)
                .stream().collect(Collectors.groupingBy(a -> a.getDataHora().toLocalDate().getDayOfWeek()));


        Map<DayOfWeek, List<DisponibilidadePainelDeSalaVO>> disponibilidadesPorDiaDaSemana = disponibilidadeRepository
                .findDisponibilidadeParaPainelDeSalas()
                .stream().collect(Collectors.groupingBy(dis -> dis.getDiaDaSemana()));

        disponibilidadesPorDiaDaSemana.entrySet().stream().forEach(entry -> {
            DayOfWeek diaDaSemana = entry.getKey();
            List<DisponibilidadePainelDeSalaVO> disponibilidades = entry.getValue();

            PainelDeSalasResponse responseDiaDaSemana = response.stream().filter(res -> res.getDiaDaSemana().equals(diaDaSemana)).findFirst().get();

            disponibilidades.forEach(disponibilidade -> {
                PainelDeSalasResponse.PainelDeSalasHorarioResponse responseHorario = responseDiaDaSemana.getHorarios()
                        .stream().filter(h -> h.getHorario().equals(disponibilidade.getHorario()))
                        .findFirst().get();

                responseHorario.adicionarOcupacao(disponibilidade.getIdPersona(), disponibilidade.getNomePersona(), disponibilidade.getSala());

                List<Atendimento> atendimentos = atendimentosPorDiaDaSemana.getOrDefault(diaDaSemana, List.of())
                        .stream().filter(a -> a.getDataHora().toLocalTime().equals(disponibilidade.getHorario())).collect(Collectors.toList());

                atendimentos.stream().forEach(atendimento -> responseHorario.adicionarAtendimento(atendimento));

            });
        });

        return response;
    }

    private void montarDiasDaSemana(List<ISalaResumidaVO> salas) {
        response = Stream.of(
                PainelDeSalasResponse.of(DayOfWeek.SUNDAY, salas),
                PainelDeSalasResponse.of(DayOfWeek.MONDAY, salas),
                PainelDeSalasResponse.of(DayOfWeek.TUESDAY, salas),
                PainelDeSalasResponse.of(DayOfWeek.WEDNESDAY, salas),
                PainelDeSalasResponse.of(DayOfWeek.THURSDAY, salas),
                PainelDeSalasResponse.of(DayOfWeek.FRIDAY, salas),
                PainelDeSalasResponse.of(DayOfWeek.SATURDAY, salas)
        ).collect(Collectors.toList());
    }
}