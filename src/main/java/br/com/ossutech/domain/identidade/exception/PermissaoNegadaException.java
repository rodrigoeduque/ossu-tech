package br.com.ossutech.domain.identidade.exception;

public class PermissaoNegadaException extends RuntimeException {

    public PermissaoNegadaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNegadaException(String acao, String motivo) {
        super(String.format("Permissão negada para %s: %s", acao, motivo));
    }
}
