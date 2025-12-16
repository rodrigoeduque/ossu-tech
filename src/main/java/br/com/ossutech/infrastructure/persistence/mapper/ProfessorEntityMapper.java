package br.com.ossutech.infrastructure.persistence.mapper;

import br.com.ossutech.domain.identidade.model.Professor;
import br.com.ossutech.domain.identidade.model.valueobjects.CPF;
import br.com.ossutech.infrastructure.persistence.entity.ProfessorEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfessorEntityMapper {

    public ProfessorEntity toEntity(Professor professor) {
        if (professor == null) {
            return null;
        }

        return ProfessorEntity.builder()
                .id(professor.getId())
                .usuarioId(professor.getUsuarioId())
                .nomeCompleto(professor.getNomeCompleto())
                .cpf(professor.getCpf().getValor())
                .graduacaoId(professor.getGraduacaoId())
                .podeAprovarPresencas(professor.isPodeAprovarPresencas())
                .podeGraduarAlunos(professor.isPodeGraduarAlunos())
                .criadoEm(professor.getCriadoEm())
                .atualizadoEm(professor.getAtualizadoEm())
                .build();
    }

    public Professor toDomain(ProfessorEntity entity) {
        if (entity == null) {
            return null;
        }

        return Professor.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .nomeCompleto(entity.getNomeCompleto())
                .cpf(CPF.of(entity.getCpf()))
                .graduacaoId(entity.getGraduacaoId())
                .podeAprovarPresencas(entity.getPodeAprovarPresencas())
                .podeGraduarAlunos(entity.getPodeGraduarAlunos())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
