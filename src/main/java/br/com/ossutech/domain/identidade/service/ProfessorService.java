package br.com.ossutech.domain.identidade.service;

import br.com.ossutech.domain.identidade.exception.CPFJaCadastradoException;
import br.com.ossutech.domain.identidade.exception.PermissaoNegadaException;
import br.com.ossutech.domain.identidade.exception.ProfessorNaoEncontradoException;
import br.com.ossutech.domain.identidade.model.Professor;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import br.com.ossutech.domain.identidade.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public Professor criarProfessor(Professor professor) {
        professor.validar();

        if (professorRepository.existePorCPF(professor.getCpf())) {
            throw new CPFJaCadastradoException(professor.getCpf().getValor());
        }

        return professorRepository.salvar(professor);
    }

    public Professor buscarPorId(UUID id) {
        return professorRepository.buscarPorId(id)
                .orElseThrow(() -> new ProfessorNaoEncontradoException(id));
    }

    public Professor buscarPorCPF(CPF cpf) {
        return professorRepository.buscarPorCPF(cpf)
                .orElseThrow(() -> new ProfessorNaoEncontradoException(cpf.getValor()));
    }

    public Professor buscarPorUsuarioId(UUID usuarioId) {
        return professorRepository.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new ProfessorNaoEncontradoException(usuarioId));
    }

    public Professor concederPermissaoAprovacao(UUID professorId) {
        Professor professor = buscarPorId(professorId);
        professor.concederPermissaoAprovacao();
        return professorRepository.salvar(professor);
    }

    public Professor revogarPermissaoAprovacao(UUID professorId) {
        Professor professor = buscarPorId(professorId);
        professor.revogarPermissaoAprovacao();
        return professorRepository.salvar(professor);
    }

    public Professor concederPermissaoGraduacao(UUID professorId) {
        Professor professor = buscarPorId(professorId);
        professor.concederPermissaoGraduacao();
        return professorRepository.salvar(professor);
    }

    public Professor revogarPermissaoGraduacao(UUID professorId) {
        Professor professor = buscarPorId(professorId);
        professor.revogarPermissaoGraduacao();
        return professorRepository.salvar(professor);
    }

    public void validarPermissaoAprovacao(UUID professorId) {
        Professor professor = buscarPorId(professorId);

        if (!professor.isPodeAprovarPresencas()) {
            throw new PermissaoNegadaException(
                    "aprovar presenças",
                    "Professor " + professor.getNomeCompleto() + " não possui essa permissão"
            );
        }
    }

    public void validarPermissaoGraduacao(UUID professorId) {
        Professor professor = buscarPorId(professorId);

        if (!professor.isPodeGraduarAlunos()) {
            throw new PermissaoNegadaException(
                    "graduar alunos",
                    "Professor " + professor.getNomeCompleto() + " não possui essa permissão"
            );
        }
    }

    public List<Professor> listarTodos() {
        return professorRepository.listarTodos();
    }

    public List<Professor> listarComPermissaoAprovacao() {
        return professorRepository.listarPorPermissaoAprovacao();
    }

    public List<Professor> listarComPermissaoGraduacao() {
        return professorRepository.listarPorPermissaoGraduacao();
    }
}
