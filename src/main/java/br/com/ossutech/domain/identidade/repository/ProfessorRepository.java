package br.com.ossutech.domain.identidade.repository;

import br.com.ossutech.domain.identidade.model.Professor;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository {

    Professor salvar(Professor professor);

    Optional<Professor> buscarPorId(UUID id);

    Optional<Professor> buscarPorCPF(CPF cpf);

    Optional<Professor> buscarPorUsuarioId(UUID usuarioId);

    boolean existePorCPF(CPF cpf);

    List<Professor> listarTodos();

    List<Professor> listarPorPermissaoAprovacao();

    List<Professor> listarPorPermissaoGraduacao();

    void deletar(UUID id);

}
