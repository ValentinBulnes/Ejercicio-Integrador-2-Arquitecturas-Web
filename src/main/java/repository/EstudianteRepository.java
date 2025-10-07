package repository;

import modelo.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    void cargarDesdeCSV();
    void insert( Estudiante estudiante);
    List<Estudiante> getEstudiantes();
}
