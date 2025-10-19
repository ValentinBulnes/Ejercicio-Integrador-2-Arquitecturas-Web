package org.example.tp3.controllers;

import org.example.tp3.dto.EstudianteDTO;
import org.example.tp3.model.Estudiante;
import org.example.tp3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteControllerJpa {
    
    @Qualifier("estudianteRepository")
    @Autowired
    private final EstudianteRepository estudianteRepository;

    public EstudianteControllerJpa(@Qualifier("estudianteRepository") EstudianteRepository repository) {
        this.estudianteRepository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.getEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }
    
    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) {
        estudianteRepository.insert(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudiante);
    }
    
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<EstudianteDTO>> getEstudiantesByGenero(@PathVariable String genero) {
        List<EstudianteDTO> estudiantes = estudianteRepository.buscarPorGenero(genero);
        return ResponseEntity.ok(estudiantes);
    }
    
    @GetMapping("/libreta/{libreta}")
    public ResponseEntity<EstudianteDTO> getEstudianteByLibreta(@PathVariable Integer libreta) {
        EstudianteDTO estudiante = estudianteRepository.buscarPorLibreta(libreta);
        if (estudiante != null) {
            return ResponseEntity.ok(estudiante);
        }
        return ResponseEntity.notFound().build();
    }
}
