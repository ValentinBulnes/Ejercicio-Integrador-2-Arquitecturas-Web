package org.example.tp3.repository;

import org.example.tp3.dto.CarreraConInscriptosDTO;
import org.example.tp3.dto.CarreraReporteDTO;
import org.example.tp3.model.Estudiante;
import org.example.tp3.model.EstudianteCarrera;
import org.example.tp3.model.EstudianteCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, EstudianteCarreraId> {

    @Query("SELECT new org.example.tp3.dto.CarreraConInscriptosDTO(" +
            "c.idCarrera, c.carrera, COUNT(ec)) " +
            "FROM EstudianteCarrera ec JOIN ec.carrera c " +
            "GROUP BY c.idCarrera, c.carrera " +
            "ORDER BY COUNT(ec) DESC")
    List<CarreraConInscriptosDTO> contarInscriptosPorCarrera();

    @Query("SELECT ec.estudiante FROM EstudianteCarrera ec " +
            "WHERE ec.carrera.idCarrera = :idCarrera AND ec.estudiante.ciudadResidencia = :ciudad")
    List<Estudiante> buscarEstudiantesPorCarreraYCiudad(@Param("idCarrera") Long idCarrera,
            @Param("ciudad") String ciudad);

    @Query("SELECT new org.example.tp3.dto.CarreraReporteDTO(" +
            "c.idCarrera, c.carrera, ec.inscripcion, COUNT(ec), " +
            "SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END)) " +
            "FROM EstudianteCarrera ec JOIN ec.carrera c " +
            "GROUP BY c.idCarrera, c.carrera, ec.inscripcion " +
            "ORDER BY c.carrera ASC, ec.inscripcion ASC")
    List<CarreraReporteDTO> reporteInscriptosYEgresados();
}