package repository;

import java.util.List;

import dto.CarreraConInscriptosDTO;

public interface EstudianteCarreraRepository {
    void cargarDesdeCSV();
    void insert(Long numeroDocumento, Long idCarrera, Integer inscripcion, Integer graduacion, Integer antiguedad);
    List<CarreraConInscriptosDTO> getCarrerasConCantidadInscriptos();
}
