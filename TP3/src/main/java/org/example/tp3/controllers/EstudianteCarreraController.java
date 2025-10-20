package org.example.tp3.controllers;

import org.example.tp3.dto.CarreraConInscriptosDTO;
import org.example.tp3.dto.CarreraReporteDTO;
import org.example.tp3.dto.EstudianteDTO;
import org.example.tp3.repository.CarreraRepository;
import org.example.tp3.repository.EstudianteCarreraRepository;
import org.example.tp3.services.EstudianteCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiante-carrera")
public class EstudianteCarreraController {

    @Autowired
    private EstudianteCarreraService estudianteCarreraService;

    @PostMapping("/matricular")
    public ResponseEntity<String> matricularEstudiante(
            @RequestParam Integer libretaUniversitaria,
            @RequestParam Long idCarrera,
            @RequestParam Integer inscripcion,
            @RequestParam Integer graduacion,
            @RequestParam Integer antiguedad
    ){
        try {
            estudianteCarreraService.matricularEstudianteEnCarrera(
                    libretaUniversitaria,idCarrera,inscripcion,graduacion,antiguedad
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("estudiante matriculado exitosamente");

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al matricular estudiante");
        }
    }

    @GetMapping("/carreras-con-inscriptos")
    public ResponseEntity<List<CarreraConInscriptosDTO>>  carrerasConInscriptos(){
        List<CarreraConInscriptosDTO> carreras = estudianteCarreraService.obtenerCarrerasConInscriptos();
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/estudiantes")
    public ResponseEntity<List<EstudianteDTO>> buscarEstudiantesPorCarreraYcidudad(
            @RequestParam Long idCarrera,
            @RequestParam String ciudad
    ){
        List<EstudianteDTO> estudiantes = estudianteCarreraService.buscarEstudiantesPorCarreraYCiudad(idCarrera,ciudad);
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/reporte-carreras")
    public ResponseEntity<List<CarreraReporteDTO>> generarReporteCarreras(){
        List<CarreraReporteDTO> reporte = estudianteCarreraService.generarReporteCarreras();
        return ResponseEntity.ok(reporte);
    }



}
