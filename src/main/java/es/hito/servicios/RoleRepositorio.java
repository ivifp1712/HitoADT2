package es.hito.servicios;

import es.hito.jpa.Role;
import es.hito.jpa.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositorio extends JpaRepository<Role, Integer> {

}
