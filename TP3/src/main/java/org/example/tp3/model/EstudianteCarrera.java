package org.example.tp3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estudianteCarrera")
public class EstudianteCarrera {
    @EmbeddedId
    private EstudianteCarreraId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("estudianteId")
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carreraId")
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @Column
    private Integer inscripcion;

    @Column
    private Integer graduacion;

    @Column
    private Integer antiguedad;

    public EstudianteCarrera() {}

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, Integer inscripcion, Integer graduacion, Integer antiguedad) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.inscripcion = inscripcion;
        this.graduacion = graduacion;
        this.antiguedad = antiguedad;
        this.id = new EstudianteCarreraId(estudiante.getNumeroDocumento(), carrera.getIdCarrera());
    }

    // Getters y Setters
    public EstudianteCarreraId getId() {
        return id;
    }

    public void setId(EstudianteCarreraId id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Integer getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Integer inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Integer getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(Integer graduacion) {
        this.graduacion = graduacion;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }
}
