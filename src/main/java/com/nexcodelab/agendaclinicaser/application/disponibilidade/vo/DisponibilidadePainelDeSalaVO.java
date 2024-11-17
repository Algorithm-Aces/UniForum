package com.nexcodelab.agendaclinicaser.application.disponibilidade.vo;

import com.nexcodelab.agendaclinicaser.application.sala.model.Sala;
import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Value
public class DisponibilidadePainelDeSalaVO {
    String idPersona;
    String nomePersona;
    String siglaPersona;
    DayOfWeek diaDaSemana;
    LocalTime horario;
    Sala sala;
}
