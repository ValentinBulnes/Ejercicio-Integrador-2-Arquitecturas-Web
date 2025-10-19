package org.example.tp3.repository;

import org.example.tp3.model.Estudiante;
import org.example.tp3.dto.EstudianteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("SELECT new org.example.tp3.dto.EstudianteDTO(" +
            "e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.libretaUniversitaria) " +
            "FROM Estudiante e WHERE e.genero = :genero")
    List<EstudianteDTO> buscarPorGenero(@Param("genero") String genero);

    @Query("SELECT new org.example.tp3.dto.EstudianteDTO(" +
            "e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.libretaUniversitaria) " +
            "FROM Estudiante e WHERE e.libretaUniversitaria = :libreta")
    EstudianteDTO buscarPorLibreta(@Param("libreta") Integer libreta);

    Estudiante findByLibretaUniversitaria(Integer libretaUniversitaria);

    List<Estudiante> findAllByOrderByApellidoAsc();

    default List<Estudiante> getEstudiantes() {
        return findAllByOrderByApellidoAsc();
    }

    default void insert(Estudiante estudiante) {
        save(estudiante);
    }
}
