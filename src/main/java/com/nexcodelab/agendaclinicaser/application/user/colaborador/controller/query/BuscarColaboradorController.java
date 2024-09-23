package com.nexcodelab.agendaclinicaser.application.user.colaborador.controller.query;

import com.nexcodelab.agendaclinicaser.application.user.colaborador.dto.response.DetalhesColaboradorResponse;
import com.nexcodelab.agendaclinicaser.application.user.colaborador.service.BuscarColaboradorUseCase;
import com.nexcodelab.agendaclinicaser.application.user.persona.vo.IPersonaResumidaVO;
import com.nexcodelab.agendaclinicaser.core.security.annotation.RolesAllowed;
import com.nexcodelab.agendaclinicaser.core.security.enums.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuscarColaboradorController {

    private final BuscarColaboradorUseCase service;

    @RolesAllowed(accessLevel = AccessLevel.CLINICA)
    @GetMapping("/colaborador/{id}")
    public ResponseEntity<DetalhesColaboradorResponse> execute(@PathVariable String id) {
        return ResponseEntity.ok(service.execute(id));
    }
}