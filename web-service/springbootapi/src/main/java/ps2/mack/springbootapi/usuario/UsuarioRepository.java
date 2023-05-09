package ps2.mack.springbootapi.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    public Optional<Usuario> findByLogin(String login);
}
