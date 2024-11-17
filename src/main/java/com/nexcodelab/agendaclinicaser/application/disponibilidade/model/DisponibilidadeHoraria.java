package com.nexcodelab.agendaclinicaser.application.disponibilidade.model;

import com.nexcodelab.agendaclinicaser.application.sala.model.Sala;
import com.nexcodelab.agendaclinicaser.shared.model.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class DisponibilidadeHoraria extends EntityBase {

    @NotNull
    private LocalTime horario;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    public DisponibilidadeHoraria(LocalTime horario) {
        this(horario, null);
    }

    public DisponibilidadeHoraria(LocalTime horario, Sala sala) {
        this.horario = horario;
        this.sala = sala;
    }

    public void atualizarSala(Sala sala) {
        this.sala = sala;
    }

    public void removerSala(){this.sala = null;}
}
