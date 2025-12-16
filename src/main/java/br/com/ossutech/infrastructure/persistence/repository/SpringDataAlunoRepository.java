package br.com.ossutech.infrastructure.persistence.repository;

import br.com.ossutech.infrastructure.persistence.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataAlunoRepository extends JpaRepository<AlunoEntity, UUID> {

    Optional<AlunoEntity> findByCpf(String cpf);

    Optional<AlunoEntity> findByUsuarioId(UUID usuarioId);

    boolean existsByCpf(String cpf);

    List<AlunoEntity> findByGraduacaoAtualId(UUID graduacaoId);

    @Query("SELECT a FROM AlunoEntity a WHERE a.usuarioId IN " +
            "(SELECT u.id FROM UsuarioEntity u WHERE u.ativo = true)")
    List<AlunoEntity> findAllAtivos();
}
