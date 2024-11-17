package com.nexcodelab.agendaclinicaser.application.agenda.geral.controller.query;

import com.nexcodelab.agendaclinicaser.application.agenda.geral.dto.response.DisponibilidadeNoHorarioResponse;
import com.nexcodelab.agendaclinicaser.application.agenda.ocupacaohorario.model.OcupacaoHorario;
import com.nexcodelab.agendaclinicaser.application.agenda.ocupacaohorario.model.repository.OcupacaoHorarioRepository;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.repository.DisponibilidadeRepository;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.vo.DisponibilidadePainelDeSalaVO;
import com.nexcodelab.agendaclinicaser.core.security.annotation.RolesAllowed;
import com.nexcodelab.agendaclinicaser.core.security.enums.AccessLevel;
import com.nexcodelab.agendaclinicaser.shared.utils.DateUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Agenda")
@RestController
@RequiredArgsConstructor
public class EstagiariosDisponiveisController {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final OcupacaoHorarioRepository ocupacaoHorarioRepository;

    @RolesAllowed(accessLevel = AccessLevel.CLINICA)
    @GetMapping("/agenda/disponibilidade-no-periodo")
    public ResponseEntity<List<DisponibilidadeNoHorarioResponse>> execute(@RequestParam String dataReferencia){
        LocalDateTime dataReferenciaFormatada = DateUtils.toLocalDateTime(dataReferencia);

        DayOfWeek diaDaSemana = dataReferenciaFormatada.getDayOfWeek();
        LocalTime horario = dataReferenciaFormatada.toLocalTime();

        List<DisponibilidadePainelDeSalaVO> disponibilidades = disponibilidadeRepository.findDisponibilidadeNaDataHora(diaDaSemana, horario);

        List<String> idPersonas = disponibilidades.stream().map(d -> d.getIdPersona()).distinct().toList();
        List<OcupacaoHorario> ocupacoesEstagiario = ocupacaoHorarioRepository.findOcupacaoesPersonasEDataHora(idPersonas, dataReferenciaFormatada);

        List<DisponibilidadeNoHorarioResponse> response = disponibilidades.stream()
                .filter(disponibilidade -> !ocupacoesEstagiario.stream().anyMatch(ocupacao -> ocupacao.getIdAgendavel().equals(disponibilidade.getIdPersona()) &&
                ocupacao.getDataHoraInicio().getDayOfWeek().equals(disponibilidade.getDiaDaSemana()) &&
                ocupacao.getDataHoraInicio().toLocalTime().equals(disponibilidade.getHorario())
        )).map(disponibilidade -> new DisponibilidadeNoHorarioResponse(disponibilidade.getIdPersona(), disponibilidade.getNomePersona(), disponibilidade.getSiglaPersona(), disponibilidade.getSala().getId(), disponibilidade.getSala().getNome())).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
