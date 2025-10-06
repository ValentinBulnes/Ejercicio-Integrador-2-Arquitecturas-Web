package repository;

public interface EstudianteCarreraRepository {
    void cargarDesdeCSV();
    void insert(Long numeroDocumento, Long idCarrera, Integer inscripcion, Integer graduacion, Integer antiguedad);
}
