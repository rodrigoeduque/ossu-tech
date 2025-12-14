package br.com.ossutech.domain.identidade.repository;

import br.com.ossutech.domain.identidade.model.Usuario;
import br.com.ossutech.domain.identidade.model.valueobjects.Email;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface (Domain Layer)
 * Define o contrato, mas não sabe COMO os dados são persistidos
 */
public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorId(UUID id);

    Optional<Usuario> buscarPorEmail(Email email);

    boolean existePorEmail(Email email);

    void deletar(UUID id);


}
