package br.com.ossutech.domain.identidade.service;

import br.com.ossutech.domain.identidade.exception.AlunoNaoEncontradoException;
import br.com.ossutech.domain.identidade.exception.CPFJaCadastradoException;
import br.com.ossutech.domain.identidade.model.Aluno;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import br.com.ossutech.domain.identidade.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public Aluno criarAluno(Aluno aluno) {
        aluno.validar();

        if (alunoRepository.existePorCpf(aluno.getCpf())) {
            throw new CPFJaCadastradoException(aluno.getCpf().getValor());
        }
        return alunoRepository.salvar(aluno);
    }

    public Aluno buscarPorId(UUID id) {
        return alunoRepository.buscarPorId(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));
    }

    public Aluno buscarPorCpf(CPF cpf) {
        return alunoRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new AlunoNaoEncontradoException("CPF", cpf.getValor()));
    }

    public Aluno buscaPorUsuarioId(UUID usuarioId) {
        return alunoRepository.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Usuário ID", usuarioId.toString()));
    }

    public Aluno registraTempoTreino(UUID alunoId, int minutos) {
        Aluno aluno = buscarPorId(alunoId);
        aluno.adicionarTempoTreino(minutos);
        return alunoRepository.salvar(aluno);
    }

    public Aluno atualizarGraduacao(UUID alunoId, UUID novaGraduacaoId) {
        Aluno aluno = buscarPorId(alunoId);

        // A lógica está na entidade
        aluno.atualizarGraduacao(novaGraduacaoId);

        return alunoRepository.salvar(aluno);
    }

    public List<Aluno> listarAtivos() {
        return alunoRepository.listarAtivos();
    }

    public List<Aluno> listarPorGraduacao(UUID graduacaoId) {
        return alunoRepository.buscarPorGraduacao(graduacaoId);
    }

    public boolean isAptoParaProximaGraduacao(UUID alunoId, int tempoMinimoNecessario) {
        Aluno aluno = buscarPorId(alunoId);
        return aluno.getTempoAcumuladoMinutos() >= tempoMinimoNecessario;
    }
}
