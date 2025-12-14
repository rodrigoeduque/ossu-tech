package br.com.ossutech.domain.identidade.model;

import br.com.ossutech.domain.identidade.model.valueobjects.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity: Usuario
 * Representa a autenticação e autorização no sistema
 */
@Getter
@Builder
@AllArgsConstructor
public class Usuario {

    private UUID id;
    private Email email;
    private String senhaHash;  // Nunca armazenar senha em texto plano!
    private TipoUsuario tipoUsuario;
    private boolean ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    /**
     * Conceito DDD: Métodos de negócio ficam na própria entidade
     */
    public void ativar() {
        this.ativo = true;
        this.atualizadoEm = LocalDateTime.now();
    }

    public void desativar() {
        this.ativo = false;
        this.atualizadoEm = LocalDateTime.now();
    }

    public boolean isAluno() {
        return TipoUsuario.ALUNO.equals(this.tipoUsuario);
    }

    public boolean isProfessor() {
        return TipoUsuario.PROFESSOR.equals(this.tipoUsuario);
    }

    /**
     * Validações de regra de negócio
     */
    public void validar() {
        if (email == null) {
            throw new IllegalStateException("Email é obrigatório");
        }
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new IllegalStateException("Senha é obrigatória");
        }
        if (tipoUsuario == null) {
            throw new IllegalStateException("Tipo de usuário é obrigatório");
        }
    }
}