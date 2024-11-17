package com.nexcodelab.agendaclinicaser.application.disponibilidade.repository;

import com.nexcodelab.agendaclinicaser.application.disponibilidade.model.DisponibilidadePersona;
import com.nexcodelab.agendaclinicaser.application.disponibilidade.vo.DisponibilidadePainelDeSalaVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DisponibilidadeRepository extends JpaRepository<DisponibilidadePersona, Long> {

    @Query("SELECT COUNT(d) > 0 FROM DisponibilidadePersona d WHERE d.personaId = :personaId")
    boolean existsDisponibilidadeEstagiario(String personaId);

    @Query("SELECT d FROM DisponibilidadePersona d WHERE d.personaId = :id")
    Optional<DisponibilidadePersona> findByPersonaId(String id);

    @Query("SELECT d FROM DisponibilidadePersona d WHERE d.personaId IN :ids")
    List<DisponibilidadePersona> findByPersonasIds(List<String> ids);

    @Query("SELECT new com.nexcodelab.agendaclinicaser.application.disponibilidade.vo.DisponibilidadePainelDeSalaVO(" +
            "   d.personaId, " +
            "   CONCAT(p.nome, ' ', p.sobrenome), " +
            "   CONCAT(SUBSTRING(p.nome, 1, 1), SUBSTRING(p.sobrenome, 1, 1)), " +
            "   dd.diaDaSemana, " +
            "   h.horario, " +
            "   h.sala) " +
            "FROM DisponibilidadePersona d " +
            "JOIN Persona p ON d.personaId = p.id " +
            "JOIN d.diaDaSemana dd " +
            "JOIN dd.horarios h " +
            "WHERE h.sala IS NOT NULL ")
    List<DisponibilidadePainelDeSalaVO> findDisponibilidadeParaPainelDeSalas();

    @Query("SELECT new com.nexcodelab.agendaclinicaser.application.disponibilidade.vo.DisponibilidadePainelDeSalaVO(" +
            "   d.personaId, " +
            "   CONCAT(p.nome, ' ', p.sobrenome), " +
            "   CONCAT(SUBSTRING(p.nome, 1, 1), SUBSTRING(p.sobrenome, 1, 1)), " +
            "   dd.diaDaSemana, " +
            "   h.horario, " +
            "   h.sala) " +
            "FROM DisponibilidadePersona d " +
            "JOIN Persona p ON d.personaId = p.id " +
            "JOIN d.diaDaSemana dd " +
            "JOIN dd.horarios h " +
            "WHERE h.sala IS NOT NULL AND dd.diaDaSemana = :diaDaSemana AND h.horario = :horario")
    List<DisponibilidadePainelDeSalaVO> findDisponibilidadeNaDataHora(DayOfWeek diaDaSemana, LocalTime horario);

}
