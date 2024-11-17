package com.nexcodelab.agendaclinicaser.application.gestaoocupacaosalas.controller.command;

import com.nexcodelab.agendaclinicaser.application.gestaoocupacaosalas.service.RemoverOcupacaoPainelDeSalaUseCase;
import com.nexcodelab.agendaclinicaser.core.security.annotation.RolesAllowed;
import com.nexcodelab.agendaclinicaser.core.security.enums.AccessLevel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Tag(name = "Painel de salas")
@RestController
@RequiredArgsConstructor
public class RemoverOcupacaoPainelDeSalaController {

    private final RemoverOcupacaoPainelDeSalaUseCase service;

    @Operation(summary = "Remover sala do estagiário")
    @RolesAllowed(accessLevel = AccessLevel.CLINICA)
    @DeleteMapping("/painel-salas")
    public ResponseEntity<Void> execute(@RequestParam String idEstagiario, @RequestParam DayOfWeek diaDaSemana, @RequestParam LocalTime horario){

        service.execute(idEstagiario, diaDaSemana, horario);

        return new ResponseEntity(null, HttpStatus.OK);
    }
}
