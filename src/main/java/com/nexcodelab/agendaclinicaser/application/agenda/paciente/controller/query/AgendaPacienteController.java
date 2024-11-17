package com.nexcodelab.agendaclinicaser.application.agenda.paciente.controller.query;

import com.nexcodelab.agendaclinicaser.application.agenda.geral.dto.response.AtendimentoAgendaResponse;
import com.nexcodelab.agendaclinicaser.application.agenda.paciente.service.AgendaPacienteUseCase;
import com.nexcodelab.agendaclinicaser.core.security.annotation.RolesAllowed;
import com.nexcodelab.agendaclinicaser.core.security.enums.AccessLevel;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Agenda")
@RestController
@RequiredArgsConstructor
public class AgendaPacienteController {

    private final AgendaPacienteUseCase service;

    @RolesAllowed(accessLevel = AccessLevel.CLINICA)
    @GetMapping("/agenda-paciente/{idPaciente}/{dataReferencia}")
    public ResponseEntity<List<AtendimentoAgendaResponse>> execute(@PathVariable String idPaciente,@PathVariable String dataReferencia){

        List<AtendimentoAgendaResponse> response = service.execute(idPaciente, dataReferencia);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
