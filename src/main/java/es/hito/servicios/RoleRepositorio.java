package es.hito.servicios;

import es.hito.jpa.Role;
import es.hito.jpa.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositorio extends JpaRepository<Role, Integer> {

    // find by usuario
    Optional<Role> findByUsuario(Usuario usuario);

}
