package org.example.tp3.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrera;

    @Column
    private String carrera;

    @Column
    private Integer duracion;

     @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
     private List<EstudianteCarrera> estudiantes = new ArrayList<>();

    public Carrera() {}

    public Carrera(String carrera, Integer duracion) {
        this.carrera = carrera;
        this.duracion = duracion;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}
