package es.hito.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.List;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String nif;

    private byte activo;

    private String apellidos;

    private String nombre;

    private String pw;

    //bi-directional many-to-one association to Role
    @OneToMany(mappedBy="usuario")
    private List<Role> roles;

    //bi-directional many-to-one association to Tarea
    @OneToMany(mappedBy="usuario")
    private List<Tarea> tareas;

    public Usuario() {
        this.nif = "";
        this.nombre = "";
        this.apellidos = "";
        this.pw = "";
        this.activo = 1;
    }

    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public byte getActivo() {
        return this.activo;
    }

    public void setActivo(byte activo) {
        this.activo = activo;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPw() {
        return this.pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Role addRole(Role role) {
        getRoles().add(role);
        role.setUsuario(this);

        return role;
    }

    public Role removeRole(Role role) {
        getRoles().remove(role);
        role.setUsuario(null);

        return role;
    }

    public List<Tarea> getTareas() {
        return this.tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Tarea addTarea(Tarea tarea) {
        getTareas().add(tarea);
        tarea.setUsuario(this);

        return tarea;
    }

    public Tarea removeTarea(Tarea tarea) {
        getTareas().remove(tarea);
        tarea.setUsuario(null);

        return tarea;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nif='" + nif + '\'' +
                ", activo=" + activo +
                ", apellidos='" + apellidos + '\'' +
                ", nombre='" + nombre + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }

    //comprobar si es administrador

}