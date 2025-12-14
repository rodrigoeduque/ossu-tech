package br.com.ossutech.domain.identidade.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("Email já cadastrado no sistema: " + email);
    }
}