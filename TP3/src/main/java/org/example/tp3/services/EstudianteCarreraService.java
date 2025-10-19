package org.example.tp3.services;

import org.example.tp3.dto.CarreraConInscriptosDTO;
import org.example.tp3.dto.CarreraReporteDTO;
import org.example.tp3.dto.EstudianteDTO;
import org.example.tp3.model.Carrera;
import org.example.tp3.model.Estudiante;
import org.example.tp3.model.EstudianteCarrera;
import org.example.tp3.model.EstudianteCarreraId;
import org.example.tp3.repository.CarreraRepository;
import org.example.tp3.repository.EstudianteCarreraRepository;
import org.example.tp3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("EstudianteCarreraService")
public class EstudianteCarreraService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;


    public void matricularEstudianteEnCarrera(Integer libretaUniversitaria, Long idCarrera,
                                              Integer inscripcion, Integer graduacion, Integer antiguedad) {

        Estudiante estudiante = estudianteRepository.findByLibretaUniversitaria(libretaUniversitaria);
        if (estudiante == null) {
            throw new RuntimeException("Estudiante no encontrado con libreta " + libretaUniversitaria);
        }

        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID " + idCarrera));

        EstudianteCarreraId id = new EstudianteCarreraId(estudiante.getNumeroDocumento(), carrera.getIdCarrera());

        EstudianteCarrera relacion = new EstudianteCarrera();
        relacion.setId(id);
        relacion.setEstudiante(estudiante);
        relacion.setCarrera(carrera);
        relacion.setInscripcion(inscripcion);
        relacion.setGraduacion(graduacion);
        relacion.setAntiguedad(antiguedad);

        estudianteCarreraRepository.save(relacion);
    }

    public List<CarreraConInscriptosDTO> obtenerCarrerasConInscriptos() {
        return estudianteCarreraRepository.contarInscriptosPorCarrera();
    }

    public List<EstudianteDTO> buscarEstudiantesPorCarreraYCiudad(long idCarrera , String ciudad) {
        List<Estudiante> estudiantes = estudianteCarreraRepository.buscarEstudiantesPorCarreraYCiudad(idCarrera, ciudad);
        return estudiantes.stream()
                .map(e -> new EstudianteDTO(
                        e.getNombre(),
                        e.getApellido(),
                        e.getEdad(),
                        e.getGenero(),
                        e.getCiudadResidencia(),
                        e.getLibretaUniversitaria()
                ))
                .collect(Collectors.toList());
    }
    public List<CarreraReporteDTO> generarReporteCarreras() {
        return estudianteCarreraRepository.reporteInscriptosYEgresados();
    }


}

