package br.com.ossutech.domain.identidade.model;

import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import br.com.ossutech.domain.identidade.model.valueobjects.Telefone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

/**
 * Aggregate Root: Aluno
 * Controla seu próprio ciclo de vida e graduação
 */
@Getter
@Builder
@AllArgsConstructor
public class Aluno {

    private UUID id;
    private UUID usuarioId;
    private String nomeCompleto;
    private CPF cpf;
    private LocalDate dataNascimento;
    private Telefone telefone;
    private String fotoUrl;
    private UUID graduacaoAtualId;
    private Integer tempoAcumuladoMinutos;
    private LocalDate dataInicio;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    /**
     * Regras de negócio: Idade mínima para praticar Jiu-Jitsu
     */
    private static final int IDADE_MINIMA = 4;

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean isMaiorDeIdade() {
        return calcularIdade() >= 18;
    }

    public void validarIdadeMinima() {
        if (calcularIdade() < IDADE_MINIMA) {
            throw new IllegalStateException(
                    "Aluno deve ter no mínimo " + IDADE_MINIMA + " anos"
            );
        }
    }

    /**
     * Adiciona tempo de treino acumulado
     */
    public void adicionarTempoTreino(int minutos) {
        if (minutos <= 0) {
            throw new IllegalArgumentException("Tempo deve ser positivo");
        }
        this.tempoAcumuladoMinutos += minutos;
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * Atualiza graduação
     */
    public void atualizarGraduacao(UUID novaGraduacaoId) {
        if (novaGraduacaoId == null) {
            throw new IllegalArgumentException("Graduação não pode ser nula");
        }
        this.graduacaoAtualId = novaGraduacaoId;
        this.atualizadoEm = LocalDateTime.now();
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
        if (dataNascimento == null) {
            throw new IllegalStateException("Data de nascimento é obrigatória");
        }
        if (dataInicio == null) {
            throw new IllegalStateException("Data de início é obrigatória");
        }

        validarIdadeMinima();
    }

    /**
     * Retorna tempo em formato legível
     */
    public String getTempoAcumuladoFormatado() {
        int horas = tempoAcumuladoMinutos / 60;
        int minutos = tempoAcumuladoMinutos % 60;
        return String.format("%d horas e %d minutos", horas, minutos);
    }
}