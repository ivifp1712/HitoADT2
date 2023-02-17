package es.hito.servicios;


import es.hito.jpa.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, String> {
}
