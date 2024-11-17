package com.nexcodelab.agendaclinicaser.application.agenda.paciente.service;

import com.nexcodelab.agendaclinicaser.application.agenda.geral.dto.response.AtendimentoAgendaResponse;
import com.nexcodelab.agendaclinicaser.application.atendimento.model.Atendimento;
import com.nexcodelab.agendaclinicaser.application.atendimento.repository.AtendimentoRepository;
import com.nexcodelab.agendaclinicaser.shared.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaPacienteUseCase {

    private final AtendimentoRepository atendimentoRepository;

    public List<AtendimentoAgendaResponse> execute(String idPaciente, String dataReferencia){

        LocalDateTime dataInicio = DateUtils.toLocalDate(dataReferencia).withDayOfMonth(1)
                .atStartOfDay();
        LocalDateTime dataFim = DateUtils.toLocalDate(dataReferencia).withDayOfMonth(dataInicio.toLocalDate().lengthOfMonth())
                .atTime(LocalTime.MAX);

        List<Atendimento> atendimentos = atendimentoRepository.findAtendimentosNoPeriodoPorPaciente(dataInicio, dataFim, idPaciente);

        return AtendimentoAgendaResponse.of(atendimentos);
    }
}
