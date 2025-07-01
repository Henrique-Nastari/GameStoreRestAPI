package ufrn.br.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.br.gamestorerest.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métod0 para buscar um usuário pelo seu login
    Optional<Usuario> findByLogin(String login);
}