package io.github.farmacia.Farmacia.db.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.farmacia.Farmacia.db.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
    Usuario findByLogin(String login);

    boolean existsByLogin(String login);

}
