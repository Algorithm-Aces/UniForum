package com.nexcodelab.agendaclinicaser.application.agenda.ocupacaohorario.model.repository;

import com.nexcodelab.agendaclinicaser.application.agenda.ocupacaohorario.model.OcupacaoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OcupacaoHorarioRepository extends JpaRepository<OcupacaoHorario, Long> {

    @Query("SELECT oc FROM OcupacaoHorario oc WHERE oc.idAgendavel in :idPersonas AND oc.dataHoraInicio = :dataHora")
    List<OcupacaoHorario> findOcupacaoesPersonasEDataHora(List<String> idPersonas, LocalDateTime dataHora);

    @Query("SELECT COUNT(oc.id) > 0 FROM OcupacaoHorario oc WHERE oc.idAgendavel = :idEstagiario AND oc.dataHoraInicio >= :dataHora")
    Boolean existTerapeutaEmServicoAposData(String idEstagiario, LocalDateTime dataHora);

}
