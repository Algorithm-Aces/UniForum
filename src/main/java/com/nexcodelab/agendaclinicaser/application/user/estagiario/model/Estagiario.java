package com.nexcodelab.agendaclinicaser.application.user.estagiario.model;

import com.nexcodelab.agendaclinicaser.application.user.persona.model.Persona;
import com.nexcodelab.agendaclinicaser.application.user.persona.model.Telefone;
import com.nexcodelab.agendaclinicaser.application.user.supervisor.model.Supervisor;
import com.nexcodelab.agendaclinicaser.application.user.usuario.model.Usuario;
import com.nexcodelab.agendaclinicaser.shared.enums.Sexo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Estagiario extends Persona {
    private Integer periodo;
    private Integer horasRequeridas;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supervisor supervisor;

    public Estagiario(Usuario usuario, Integer horasRequeridas, String nome, String sobrenome, LocalDate dataNascimento, Sexo sexo, String email, String ddd, String numero, Supervisor supervisor) {
        super(nome, sobrenome, dataNascimento, sexo, email, new Telefone(ddd, numero), usuario);
        this.horasRequeridas = horasRequeridas;
        this.supervisor =  supervisor;
    }

    public String getNomeCompletoSupervisor(){
        return this.supervisor.getNome() + " "  + this.supervisor.getSobrenome();
    }

    public String getSiglaSupervisor(){
        return this.supervisor.getSigla();
    }
}
