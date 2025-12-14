package br.com.ossutech.domain.identidade.model.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object que representa um CPF válido.
 * Imutável e auto-validável.
 */
@Getter
@EqualsAndHashCode
public class CPF {

    private final String valor;

    public CPF(String valor) {
        this.valor = validarEFormatar(valor);
    }

    /**
     * Conceito DDD: Value Objects são criados através de factory methods
     * que deixam clara a intenção de criação
     */
    public static CPF of(String valor) {
        return new CPF(valor);
    }

    private String validarEFormatar(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }

        // Remove formatação (pontos e traços)
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos");
        }

        // Validação básica (todos dígitos iguais)
        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Validação completa dos dígitos verificadores
        int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 2; i++) {
            int soma = 0;
            for (int j = 0; j < pesos.length - i; j++) {
                soma += Character.getNumericValue(cpfLimpo.charAt(j)) * pesos[j];
            }
            int digitoVerificador = 11 - (soma % 11);
            if (digitoVerificador >= 10) {
                digitoVerificador = 0;
            }
            if (digitoVerificador != Character.getNumericValue(cpfLimpo.charAt(9 + i))) {
                throw new IllegalArgumentException("CPF inválido");
            }
        }


        // Retorna formatado: 000.000.000-00
        return String.format("%s.%s.%s-%s",
                cpfLimpo.substring(0, 3),
                cpfLimpo.substring(3, 6),
                cpfLimpo.substring(6, 9),
                cpfLimpo.substring(9, 11));
    }

    public String getValorSemFormatacao() {
        return valor.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return valor;
    }
}