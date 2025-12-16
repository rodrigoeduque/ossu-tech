package br.com.ossutech.infrastructure.persistence.mapper;

import br.com.ossutech.domain.identidade.model.Aluno;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import br.com.ossutech.domain.identidade.model.valueobjects.Telefone;
import br.com.ossutech.infrastructure.persistence.entity.AlunoEntity;
import org.springframework.stereotype.Component;

@Component
public class AlunoEntityMapper {

    public AlunoEntity toEntity(Aluno aluno) {
        if (aluno == null) {
            return null;
        }

        return AlunoEntity.builder()
                .id(aluno.getId())
                .usuarioId(aluno.getUsuarioId())
                .nomeCompleto(aluno.getNomeCompleto())
                .cpf(aluno.getCpf().getValor())
                .dataNascimento(aluno.getDataNascimento())
                .telefone(aluno.getTelefone() != null ? aluno.getTelefone().getValor() : null)
                .fotoUrl(aluno.getFotoUrl())
                .graduacaoAtualId(aluno.getGraduacaoAtualId())
                .tempoAcumuladoMinutos(aluno.getTempoAcumuladoMinutos())
                .dataInicio(aluno.getDataInicio())
                .criadoEm(aluno.getCriadoEm())
                .atualizadoEm(aluno.getAtualizadoEm())
                .build();
    }

    public Aluno toDomain(AlunoEntity entity) {
        if (entity == null) {
            return null;
        }

        return Aluno.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .nomeCompleto(entity.getNomeCompleto())
                .cpf(CPF.of(entity.getCpf()))
                .dataNascimento(entity.getDataNascimento())
                .telefone(entity.getTelefone() != null ? Telefone.of(entity.getTelefone()) : null)
                .fotoUrl(entity.getFotoUrl())
                .graduacaoAtualId(entity.getGraduacaoAtualId())
                .tempoAcumuladoMinutos(entity.getTempoAcumuladoMinutos())
                .dataInicio(entity.getDataInicio())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}