package com.nexcodelab.agendaclinicaser.application.agenda.ocupacaohorario.model;

import com.nexcodelab.agendaclinicaser.application.agenda.ocupacaohorario.model.enums.TipoOcupacaoHorario;
import com.nexcodelab.agendaclinicaser.application.atendimento.model.EstagiarioEmServico;
import com.nexcodelab.agendaclinicaser.application.atendimento.model.LocalDeTerapia;
import com.nexcodelab.agendaclinicaser.application.atendimento.model.PacienteEmServico;
import com.nexcodelab.agendaclinicaser.shared.model.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OcupacaoHorario extends EntityBase {
    private TipoOcupacaoHorario tipoOcupacao;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    private String idAgendavel;

    // ACONTECENDO_SERVICO
    @ManyToOne private LocalDeTerapia localDeTerapia;

    // PRESTANDO_SERVICO
    @ManyToOne private EstagiarioEmServico estagiarioEmServico;

    // RECEBENDO_SERVICO
    @ManyToOne private PacienteEmServico pacienteEmServico;

    public static OcupacaoHorario ofAcontecendoServico (LocalDateTime dataHora,
                                                        LocalDeTerapia localDeTerapia) {
        return OcupacaoHorario.builder()
                .dataHoraInicio(dataHora)
                .dataHoraFim(dataHora.plusHours(1L))
                .localDeTerapia(localDeTerapia)
                .tipoOcupacao(TipoOcupacaoHorario.ACONTECENDO_SERVICO)
                .build();
    }

    public static OcupacaoHorario ofPrestandoServico (LocalDateTime dataHora,
                                                      EstagiarioEmServico estagiarioEmServico) {
        return OcupacaoHorario.builder()
                .dataHoraInicio(dataHora)
                .dataHoraFim(dataHora.plusHours(1L))
                .estagiarioEmServico(estagiarioEmServico)
                .tipoOcupacao(TipoOcupacaoHorario.PRESTANDO_SERVICO)
                .build();
    }

    public static OcupacaoHorario ofRecebendoServico (LocalDateTime dataHora,
                                                      PacienteEmServico pacienteEmServico) {
        return OcupacaoHorario.builder()
                .dataHoraInicio(dataHora)
                .dataHoraFim(dataHora.plusHours(1L))
                .pacienteEmServico(pacienteEmServico)
                .tipoOcupacao(TipoOcupacaoHorario.RECEBENDO_SERVICO)
                .build();
    }
}
