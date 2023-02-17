package es.hito.servicios;

import es.hito.jpa.Role;
import es.hito.jpa.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Optional;
@Service
@ApplicationScope
public class RoleService {
    private RoleRepositorio roles;

    public RoleService(RoleRepositorio roles) {
        this.roles = roles;
    }

    public void guardarRol(Role rol) {roles.save(rol);
    }
}
