package br.com.ossutech.domain.identidade.model.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Telefone {

    private final String valor;

    public Telefone(String valor) {
        this.valor = validarEFormatar(valor);
    }

    private String validarEFormatar(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }

        // Remove tudo que não é número
        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");

        // Valida tamanho (10 ou 11 dígitos - com ou sem 9 na frente)
        if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
            throw new IllegalArgumentException("Telefone deve ter 10 ou 11 dígitos");
        }

        // Formata: (00) 00000-0000 ou (00) 0000-0000
        if (telefoneLimpo.length() == 11) {
            return String.format("(%s) %s-%s",
                    telefoneLimpo.substring(0, 2),
                    telefoneLimpo.substring(2, 7),
                    telefoneLimpo.substring(7, 11));
        } else {
            return String.format("(%s) %s-%s",
                    telefoneLimpo.substring(0, 2),
                    telefoneLimpo.substring(2, 6),
                    telefoneLimpo.substring(6, 10));
        }
    }

    public String getValorSemFormatacao() {
        return valor.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return valor;
    }

    public static Telefone of(String valor) {
        return new Telefone(valor);
    }
}