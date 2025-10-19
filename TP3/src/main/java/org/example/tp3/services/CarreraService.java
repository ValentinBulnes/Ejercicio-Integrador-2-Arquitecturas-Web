package org.example.tp3.services;

import org.example.tp3.model.Carrera;
import org.example.tp3.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("carreraService")
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    public Carrera crearCarrera(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    public List<Carrera> listarCarreras() {
        return carreraRepository.findAll();
    }

    public Carrera buscarPorId(Long id) {
        return carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
    }

    public Carrera actualizarCarrera(Long id, Carrera datos) {
        Carrera carrera = buscarPorId(id);
        carrera.setCarrera(datos.getCarrera());
        carrera.setDuracion(datos.getDuracion());
        return carreraRepository.save(carrera);
    }

    public void eliminarCarrera(Long id) {
        carreraRepository.deleteById(id);
    }
}
