package com.nexcodelab.agendaclinicaser.application.disponibilidade.controller.command;

import com.nexcodelab.agendaclinicaser.application.disponibilidade.dto.request.CadastrarDisponibilidadeRequest;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.service.CadastrarDisponibilidadeUseCase;
import com.nexcodelab.agendaclinicaser.core.security.annotation.RolesAllowed;
import com.nexcodelab.agendaclinicaser.core.security.enums.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CadastrarDisponibilidadeController {

    private final CadastrarDisponibilidadeUseCase service;

    @RolesAllowed(accessLevel = AccessLevel.ADMIN)
    @PostMapping("/disponibilidade")
    public ResponseEntity<Void> execute(@RequestBody @Valid CadastrarDisponibilidadeRequest request){
        service.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
