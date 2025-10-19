package org.example.tp3.controllers;

import org.example.tp3.dto.EstudianteDTO;
import org.example.tp3.model.Estudiante;
import org.example.tp3.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }
    
    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) {
        estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudiante);
    }
    
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<EstudianteDTO>> getEstudiantesByGenero(@PathVariable String genero) {
        List<EstudianteDTO> estudiantes = estudianteService.buscarPorGenero(genero);
        return ResponseEntity.ok(estudiantes);
    }
    
    @GetMapping("/libreta/{libreta}")
    public ResponseEntity<EstudianteDTO> getEstudianteByLibreta(@PathVariable Integer libreta) {
        EstudianteDTO estudiante = estudianteService.buscarPorLibreta(libreta);
        if (estudiante != null) {
            return ResponseEntity.ok(estudiante);
        }
        return ResponseEntity.notFound().build();
    }
}
