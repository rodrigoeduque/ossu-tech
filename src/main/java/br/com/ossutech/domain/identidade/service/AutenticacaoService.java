package br.com.ossutech.domain.identidade.service;

import br.com.ossutech.domain.identidade.exception.EmailJaCadastradoException;
import br.com.ossutech.domain.identidade.exception.UsuarioNaoEncontradoException;
import br.com.ossutech.domain.identidade.model.Usuario;
import br.com.ossutech.domain.identidade.model.valueobjects.Email;
import br.com.ossutech.domain.identidade.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        usuario.validar();

        if (usuarioRepository.existePorEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException(usuario.getEmail().getValor());
        }

        return usuarioRepository.salvar(usuario);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public Usuario buscarPorEmail(Email email) {
        return usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(email.getValor()));
    }

    public Usuario ativarUsuario(UUID id) {
        Usuario usuario = buscarPorId(id);
        usuario.ativar();
        return usuarioRepository.salvar(usuario);
    }

    public Usuario desativarUsuario(UUID id) {
        Usuario usuario = buscarPorId(id);
        usuario.desativar();
        return usuarioRepository.salvar(usuario);
    }

    public void validarUsuarioAtivo(UUID id) {
        Usuario usuario = buscarPorId(id);

        if (!usuario.isAtivo()) {
            throw new IllegalStateException("Usuário está inativo");
        }
    }
}