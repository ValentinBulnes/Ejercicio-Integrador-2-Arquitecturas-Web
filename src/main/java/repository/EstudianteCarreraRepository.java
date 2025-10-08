package repository;

import java.util.List;

import dto.CarreraConInscriptosDTO;
import dto.EstudianteDTO;
import modelo.Estudiante;

public interface EstudianteCarreraRepository {
    void cargarDesdeCSV();
    void insert(Long numeroDocumento, Long idCarrera, Integer inscripcion, Integer graduacion, Integer antiguedad);
    List<CarreraConInscriptosDTO> getCarrerasConCantidadInscriptos();
    List<EstudianteDTO> obtenerEstudiantesPorCarreraYCiudad(Long idCarrera, String ciudad);
}
