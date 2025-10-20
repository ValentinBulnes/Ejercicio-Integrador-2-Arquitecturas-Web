package org.example.tp3.controllers;

import org.example.tp3.model.Carrera;
import org.example.tp3.services.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @PostMapping
    public ResponseEntity<Carrera> crear(@RequestBody Carrera carrera) {
        Carrera creada = carreraService.crearCarrera(carrera);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<Carrera>> listar() {
        return ResponseEntity.ok(carreraService.listarCarreras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> obtener(@PathVariable Long id) {
        Carrera carrera = carreraService.buscarPorId(id);
        return ResponseEntity.ok(carrera);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrera> actualizar(@PathVariable Long id, @RequestBody Carrera datos) {
        Carrera actualizada = carreraService.actualizarCarrera(id, datos);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carreraService.eliminarCarrera(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        String msg = ex.getMessage() != null ? ex.getMessage() : "Error";
        if (msg.toLowerCase().contains("no encontrada")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
}
