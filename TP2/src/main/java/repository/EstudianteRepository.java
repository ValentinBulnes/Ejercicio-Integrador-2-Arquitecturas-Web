package repository;

import dto.EstudianteDTO;
import modelo.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    void cargarDesdeCSV();
    void insert( Estudiante estudiante);
    List<Estudiante> getEstudiantes();
    EstudianteDTO buscarPorLibreta(Integer libreta);
    List<EstudianteDTO> buscarPorGenero(String genero);
}
