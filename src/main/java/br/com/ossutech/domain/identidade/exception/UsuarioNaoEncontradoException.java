package br.com.ossutech.domain.identidade.exception;

import java.util.UUID;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(UUID id) {
        super("Usuário não encontrado com ID: " + id);
    }

    public UsuarioNaoEncontradoException(String email) {
        super("Usuário não encontrado com email: " + email);
    }
}