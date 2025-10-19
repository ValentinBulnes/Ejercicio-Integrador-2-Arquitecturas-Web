package org.example.tp3.services;

import org.example.tp3.dto.EstudianteDTO;
import org.example.tp3.model.Estudiante;
import org.example.tp3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import java.util.List;



@Service("estudianteService")
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteRepository.getEstudiantes();
    }
    
    public void guardarEstudiante(Estudiante estudiante) {
        estudianteRepository.insert(estudiante);
    }
    
    public List<EstudianteDTO> buscarPorGenero(String genero) {
        return estudianteRepository.buscarPorGenero(genero);
    }
    
    public EstudianteDTO buscarPorLibreta(Integer libreta) {
        return estudianteRepository.buscarPorLibreta(libreta);
    }
}
