package org.example.tp3.util;

import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.tp3.model.Carrera;
import org.example.tp3.model.Estudiante;
import org.example.tp3.model.EstudianteCarrera;
import org.example.tp3.repository.CarreraRepository;
import org.example.tp3.repository.EstudianteCarreraRepository;
import org.example.tp3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Component
public class CargaCSV implements CommandLineRunner {
     @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
         if (carreraRepository.count() == 0) {
            cargarCarreras();
        }
        if (estudianteRepository.count() == 0) {
            cargarEstudiantes();
        }
         if (estudianteCarreraRepository.count() == 0) {
            cargarEstudianteCarreras();
        }
    }

     private void cargarCarreras() throws Exception {
        try (Reader reader = new InputStreamReader(new ClassPathResource("csv/carreras.csv").getInputStream())) {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader);
            for (CSVRecord row : parser) {
                Carrera c = new Carrera(
                        row.get("carrera"),
                        Integer.parseInt(row.get("duracion"))
                );
                carreraRepository.save(c);
            }
            System.out.println("Carreras cargadas exitosamente desde CSV.");
        }
    }

    private void cargarEstudiantes() throws Exception {
        try (Reader reader = new InputStreamReader(new ClassPathResource("csv/estudiantes.csv").getInputStream(), StandardCharsets.UTF_8)) {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader);
            for (CSVRecord row : parser) {
                Estudiante e = new Estudiante();
                e.setNumeroDocumento(Long.parseLong(row.get("DNI")));
                e.setNombre(row.get("nombre"));
                e.setApellido(row.get("apellido"));
                e.setEdad(Integer.parseInt(row.get("edad")));
                e.setGenero(row.get("genero"));
                e.setCiudadResidencia(row.get("ciudad"));
                e.setLibretaUniversitaria(Integer.parseInt(row.get("LU")));
                estudianteRepository.save(e);
            }
            System.out.println("Estudiantes cargados exitosamente desde CSV.");
        }
    }

     private void cargarEstudianteCarreras() throws Exception {
        try (Reader reader = new InputStreamReader(new ClassPathResource("csv/estudianteCarrera.csv").getInputStream())) {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader);
            for (CSVRecord row : parser) {
                Long idEstudiante = Long.parseLong(row.get("id_estudiante"));
                Long idCarrera = Long.parseLong(row.get("id_carrera"));

                Estudiante estudiante = estudianteRepository.findById(idEstudiante).orElse(null);
                Carrera carrera = carreraRepository.findById(idCarrera).orElse(null);

                if (estudiante != null && carrera != null) {
                    EstudianteCarrera ec = new EstudianteCarrera(
                            estudiante,
                            carrera,
                            Integer.valueOf(row.get("inscripcion")),
                            Integer.valueOf(row.get("graduacion")),
                            Integer.valueOf(row.get("antiguedad"))
                    );
                    estudianteCarreraRepository.save(ec);
                } else {
                    System.out.println("Omitiendo registro de EstudianteCarrera: ID no encontrado (Estudiante=" + idEstudiante + ", Carrera=" + idCarrera + ")");
                }
            }
            System.out.println("Relaciones Estudiante-Carrera cargadas exitosamente desde CSV.");
        }
    }
}

