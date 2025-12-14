package br.com.ossutech.domain.identidade.exception;

public class CPFJaCadastradoException extends RuntimeException {

    public CPFJaCadastradoException(String cpf) {
        super("CPF já cadastrado no sistema: " + cpf);
    }
}