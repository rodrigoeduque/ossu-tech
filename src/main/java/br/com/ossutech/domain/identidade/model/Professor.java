package br.com.ossutech.domain.identidade.model;

import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Professor
 * Controla permissões e responsabilidades
 */
@Getter
@Builder
@AllArgsConstructor
public class Professor {

    private UUID id;
    private UUID usuarioId;
    private String nomeCompleto;
    private CPF cpf;
    private UUID graduacaoId;
    private boolean podeAprovarPresencas;
    private boolean podeGraduarAlunos;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    /**
     * Concede permissão para aprovar presenças
     */
    public void concederPermissaoAprovacao() {
        this.podeAprovarPresencas = true;
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * Revoga permissão para aprovar presenças
     */
    public void revogarPermissaoAprovacao() {
        this.podeAprovarPresencas = false;
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * Concede permissão para graduar alunos
     */
    public void concederPermissaoGraduacao() {
        this.podeGraduarAlunos = true;
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * Revoga permissão para graduar alunos
     */
    public void revogarPermissaoGraduacao() {
        this.podeGraduarAlunos = false;
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * Verifica se pode realizar determinada ação
     */
    public void validarPermissaoAprovacao() {
        if (!podeAprovarPresencas) {
            throw new IllegalStateException(
                    "Professor não possui permissão para aprovar presenças"
            );
        }
    }

    public void validarPermissaoGraduacao() {
        if (!podeGraduarAlunos) {
            throw new IllegalStateException(
                    "Professor não possui permissão para graduar alunos"
            );
        }
    }

    /**
     * Validações gerais
     */
    public void validar() {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new IllegalStateException("Nome completo é obrigatório");
        }
        if (cpf == null) {
            throw new IllegalStateException("CPF é obrigatório");
        }
    }
}