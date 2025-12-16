package br.com.ossutech.infrastructure.persistence.repository;

import br.com.ossutech.infrastructure.persistence.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataProfessorRepository extends JpaRepository<ProfessorEntity, UUID> {

    Optional<ProfessorEntity> findByCpf(String cpf);

    Optional<ProfessorEntity> findByUsuarioId(UUID usuarioId);

    boolean existsByCpf(String cpf);

    List<ProfessorEntity> findByPodeAprovarPresencasTrue();

    List<ProfessorEntity> findByPodeGraduarAlunosTrue();
}