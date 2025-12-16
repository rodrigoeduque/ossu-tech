package br.com.ossutech.infrastructure.persistence.repository;

import br.com.ossutech.domain.identidade.model.Professor;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import br.com.ossutech.domain.identidade.repository.ProfessorRepository;
import br.com.ossutech.infrastructure.persistence.mapper.ProfessorEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaProfessorRepositoryImpl implements ProfessorRepository {

    private final SpringDataProfessorRepository springDataRepository;
    private final ProfessorEntityMapper mapper;

    @Override
    public Professor salvar(Professor professor) {
        var entity = mapper.toEntity(professor);
        var savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Professor> buscarPorId(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Professor> buscarPorCPF(CPF cpf) {
        return springDataRepository.findByCpf(cpf.getValor())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Professor> buscarPorUsuarioId(UUID usuarioId) {
        return springDataRepository.findByUsuarioId(usuarioId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePorCPF(CPF cpf) {
        return springDataRepository.existsByCpf(cpf.getValor());
    }

    @Override
    public List<Professor> listarTodos() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Professor> listarPorPermissaoAprovacao() {
        return springDataRepository.findByPodeAprovarPresencasTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Professor> listarPorPermissaoGraduacao() {
        return springDataRepository.findByPodeGraduarAlunosTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        springDataRepository.deleteById(id);
    }
}
