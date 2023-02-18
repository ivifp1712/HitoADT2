package es.hito.jpa;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String rol;

    @ManyToOne
    @JoinColumn(name="nif")
    private Usuario usuario;

    public Role() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Rol " + getRol();
    }



}