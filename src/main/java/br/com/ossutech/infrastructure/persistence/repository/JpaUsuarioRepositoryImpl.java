package br.com.ossutech.infrastructure.persistence.repository;

import br.com.ossutech.domain.identidade.model.Usuario;
import br.com.ossutech.domain.identidade.model.valueobjects.Email;
import br.com.ossutech.domain.identidade.repository.UsuarioRepository;
import br.com.ossutech.infrastructure.persistence.mapper.UsuarioEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaUsuarioRepositoryImpl implements UsuarioRepository {

    private final SpringDataUsuarioRepository springDataRepository;
    private final UsuarioEntityMapper mapper;

    @Override
    public Usuario salvar(Usuario usuario) {
        var entity = mapper.toEntity(usuario);
        var savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(Email email) {
        return springDataRepository.findByEmail(email.getValor())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePorEmail(Email email) {
        return springDataRepository.existsByEmail(email.getValor());
    }

    @Override
    public void deletar(UUID id) {
        springDataRepository.deleteById(id);
    }
}
