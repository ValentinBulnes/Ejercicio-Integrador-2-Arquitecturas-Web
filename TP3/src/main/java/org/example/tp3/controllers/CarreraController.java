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

    // Inyección automática del servicio CarreraService
    @Autowired
    private CarreraService carreraService;

    // Metodo POST
    @PostMapping
    public ResponseEntity<Carrera> crear(@RequestBody Carrera carrera){
        // Llama al servicio para crear una nueva carrera en la base de datos
        Carrera creada = carreraService.crearCarrera(carrera);
        // Devuelve la carrera creada con código HTTP 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // Metodo GET
    @GetMapping
    public ResponseEntity<List<Carrera>> listar(){
        // Llama al servicio para obtener todas las carreras
        return ResponseEntity.ok(carreraService.listarCarreras());
    }

    // Metodo GET (obtener por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Carrera> obtener(@PathVariable Long id){
        // Llama al servicio para buscar la carrera por su ID
        Carrera carrera = carreraService.buscarPorId(id);
        // Devuelve la carrera encontrada con código HTTP 200 (OK)
        return ResponseEntity.ok(carrera);
    }

    // Metodo PUT
    @PutMapping("/{id}")
    public ResponseEntity<Carrera> actualizar(@PathVariable Long id, @RequestBody Carrera datos){
        // Llama al servicio para actualizar los datos de la carrera
        Carrera actualizada = carreraService.actualizarCarrera(id, datos);
        // Devuelve la carrera actualizada con código HTTP 200 (OK
        return ResponseEntity.ok(actualizada);
    }

    // Metodo DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        // Llama al servicio para eliminar la carrera
        carreraService.eliminarCarrera(id);
        // Devuelve un código HTTP 204 (No Content), sin cuerpo de respuesta
        return ResponseEntity.noContent().build();
    }

    // Manejo de excepciones
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRunTime(RuntimeException ex){
        String mensaje = ex.getMessage() != null ? ex.getMessage() : "Ocurrió un error inesperado en el servidor.";
        if (mensaje.toLowerCase().contains("no encontrada")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la carrera solicitada.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solicitud inválida. Verificá los datos enviados.");
    }
}
