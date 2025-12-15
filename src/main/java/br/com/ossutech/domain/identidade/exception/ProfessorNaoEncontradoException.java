package br.com.ossutech.domain.identidade.exception;

import java.util.UUID;

/**
 * Exception lançada quando um aluno não é encontrado
 */
public class ProfessorNaoEncontradoException extends RuntimeException {

    public ProfessorNaoEncontradoException(UUID id) {
        super("Professor não encontrado com ID: " + id);
    }

    public ProfessorNaoEncontradoException(String cpf) {
        super("Professor não encontrado com CPF: " + cpf);
    }

    public ProfessorNaoEncontradoException(String campo, String valor) {
        super(String.format("Aluno não encontrado com %s: %s", campo, valor));
    }
}