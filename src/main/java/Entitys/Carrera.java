package Entitys;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrera;

    @Column
    private String carrera;

    @Column
    private Integer duracion;

    /// @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    /// private List<EstudianteCarrera> estudiantes = new ArrayList<>();

    public Carrera() {}

    public Carrera(String carrera, Integer duracion) {
        this.carrera = carrera;
        this.duracion = duracion;
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

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
