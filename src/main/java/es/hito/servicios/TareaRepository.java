package es.hito.servicios;


import es.hito.jpa.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TareaRepository extends JpaRepository<Tarea, String> {

    @Modifying
    @Transactional
    @Query("delete from Tarea t where t.usuario.nif = :nif")
    void deleteByNif(@Param("nif") String nif);
}
