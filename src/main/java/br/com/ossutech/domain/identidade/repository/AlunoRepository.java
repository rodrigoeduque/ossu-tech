package br.com.ossutech.domain.identidade.repository;

import br.com.ossutech.domain.identidade.model.Aluno;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlunoRepository {

    Aluno salvar(Aluno aluno);

    Optional<Aluno> buscarPorId(UUID id);

    Optional<Aluno> buscarPorCpf(CPF cpf);

    List<Aluno> listarTodos();

    List<Aluno> listarAtivos();

    List<Aluno> buscarPorGraduacao(UUID graduacaoId);

    void deletar(UUID id);

}
