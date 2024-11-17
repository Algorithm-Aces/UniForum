package com.nexcodelab.agendaclinicaser.application.agenda.geral.dto.response;

import lombok.Value;

@Value
public class DisponibilidadeNoHorarioResponse {
    String idEstagiario;
    String nomeEstagiario;
    String siglaEstagiario;

    String salaId;
    String nomeSala;
}
