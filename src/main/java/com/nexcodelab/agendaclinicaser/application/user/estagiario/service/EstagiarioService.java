package com.nexcodelab.agendaclinicaser.application.user.estagiario.service;

import com.nexcodelab.agendaclinicaser.application.user.estagiario.dto.request.CadastrarEstagiarioRequest;
import com.nexcodelab.agendaclinicaser.application.user.estagiario.model.Estagiario;
import com.nexcodelab.agendaclinicaser.application.user.persona.service.PersonaService;
import com.nexcodelab.agendaclinicaser.application.user.supervisor.model.Supervisor;
import com.nexcodelab.agendaclinicaser.application.user.supervisor.repository.SupervisorRepository;
import com.nexcodelab.agendaclinicaser.application.user.usuario.model.Usuario;
import com.nexcodelab.agendaclinicaser.application.user.usuario.model.enums.Role;
import com.nexcodelab.agendaclinicaser.application.user.usuario.service.CadastrarUsuarioUseCase;
import com.nexcodelab.agendaclinicaser.core.exceptionhandler.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstagiarioService extends PersonaService<Estagiario> {

    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    private final SupervisorRepository supervisorRepository;

    public Estagiario execute(CadastrarEstagiarioRequest request) {
        Usuario usuario = cadastrarUsuarioUseCase.execute(request.getUsername(), request.getPassword(), Role.ESTAGIARIO);
        Supervisor supervisor = supervisorRepository.findById(request.getIdSupervisor())
                .orElseThrow(() -> new ResourceNotFoundException("Supervisor não encontrado"));

        return super.cadastrar(toEstagiario(request, usuario, supervisor));
    }

    private Estagiario toEstagiario(CadastrarEstagiarioRequest request, Usuario usuario, Supervisor supervisor) {
        return new Estagiario(
                usuario,
                request.getHorasRequeridas(),
                request.getNome(),
                request.getSobrenome(),
                request.getDataNascimento(),
                request.getSexo(),
                request.getEmail(),
                request.getDdd(),
                request.getNumeroTelefone(),
                supervisor

        );
    }

}
