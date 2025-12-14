package br.com.ossutech.domain.identidade.model.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class Email {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String valor;

    public Email(String valor) {
        this.valor = validar(valor);
    }

    private String validar(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }

        String emailLimpo = email.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(emailLimpo).matches()) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }

        return emailLimpo;
    }

    @Override
    public String toString() {
        return valor;
    }

    public static Email of(String valor) {
        return new Email(valor);
    }
}