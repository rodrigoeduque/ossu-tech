package br.com.ossutech.domain.identidade.exception;

import java.util.UUID;

/**
 * Exception lançada quando um aluno não é encontrado
 */
public class AlunoNaoEncontradoException extends RuntimeException {

    public AlunoNaoEncontradoException(UUID id) {
        super("Aluno não encontrado com ID: " + id);
    }

    public AlunoNaoEncontradoException(String cpf) {
        super("Aluno não encontrado com CPF: " + cpf);
    }

    public AlunoNaoEncontradoException(String campo, String valor) {
        super(String.format("Aluno não encontrado com %s: %s", campo, valor));
    }
}