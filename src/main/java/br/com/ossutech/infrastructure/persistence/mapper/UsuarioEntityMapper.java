package br.com.ossutech.infrastructure.persistence.mapper;

import br.com.ossutech.domain.identidade.model.TipoUsuario;
import br.com.ossutech.domain.identidade.model.Usuario;
import br.com.ossutech.domain.identidade.model.valueobjects.Email;
import br.com.ossutech.infrastructure.persistence.entity.TipoUsuarioEnum;
import br.com.ossutech.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioEntity.builder()
                .id(usuario.getId())
                .email(usuario.getEmail().getValor())
                .senhaHash(usuario.getSenhaHash())
                .tipoUsuario(toTipoUsuarioEnum(usuario.getTipoUsuario()))
                .ativo(usuario.isAtivo())
                .criadoEm(usuario.getCriadoEm())
                .atualizadoEm(usuario.getAtualizadoEm())
                .build();
    }

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        return Usuario.builder()
                .id(entity.getId())
                .email(Email.of(entity.getEmail()))
                .senhaHash(entity.getSenhaHash())
                .tipoUsuario(toTipoUsuario(entity.getTipoUsuario()))
                .ativo(entity.getAtivo())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }

    private TipoUsuarioEnum toTipoUsuarioEnum(TipoUsuario tipo) {
        return TipoUsuarioEnum.valueOf(tipo.name());
    }

    private TipoUsuario toTipoUsuario(TipoUsuarioEnum tipo) {
        return TipoUsuario.valueOf(tipo.name());
    }
}