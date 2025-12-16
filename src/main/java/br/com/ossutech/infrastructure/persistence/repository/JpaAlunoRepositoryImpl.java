package br.com.ossutech.infrastructure.persistence.repository;

import br.com.ossutech.domain.identidade.model.Aluno;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import br.com.ossutech.domain.identidade.repository.AlunoRepository;
import br.com.ossutech.infrastructure.persistence.mapper.AlunoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaAlunoRepositoryImpl implements AlunoRepository {

    private final SpringDataAlunoRepository springDataRepository;
    private final AlunoEntityMapper mapper;

    @Override
    public Aluno salvar(Aluno aluno) {
        var entity = mapper.toEntity(aluno);
        var savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Aluno> buscarPorId(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Aluno> buscarPorCpf(CPF cpf) {
        return springDataRepository.findByCpf(cpf.getValor())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Aluno> buscarPorUsuarioId(UUID usuarioId) {
        return springDataRepository.findByUsuarioId(usuarioId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePorCpf(CPF cpf) {
        return springDataRepository.existsByCpf(cpf.getValor());
    }

    @Override
    public List<Aluno> listarAtivos() {
        return springDataRepository.findAllAtivos().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Aluno> listarTodos() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Aluno> buscarPorGraduacao(UUID graduacaoId) {
        return springDataRepository.findByGraduacaoAtualId(graduacaoId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        springDataRepository.deleteById(id);
    }
}
