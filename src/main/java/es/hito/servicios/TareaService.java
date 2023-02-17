package es.hito.servicios;

import es.hito.jpa.Role;
import es.hito.jpa.Tarea;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class TareaService {
    private TareaRepository tareas;

    public TareaService(TareaRepository tareas) {
        this.tareas = tareas;
    }

    public void guardarTarea(Tarea tarea) {
        tareas.save(tarea);
    }

    public Iterable<Tarea> obtenerTareas() {
        return tareas.findAll();
    }

    public Tarea obtenerTareaPorId(String id) {
        if (tareas.findById(id).isPresent())
            return tareas.findById(id).get();
        else
            return null;
    }

    public void borrarTarea(String id) {
        tareas.deleteById(id);
    }

    public void borrarTareas() {
        tareas.deleteAll();
    }

    public void actualizarTarea(Tarea tarea) {
        tareas.save(tarea);
    }

}
