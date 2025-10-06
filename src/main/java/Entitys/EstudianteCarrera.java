package Entitys;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class EstudianteCarrera {
    @EmbeddedId
    private EstudianteCarreraId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("estudianteId")
    @JoinColumn
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carreraId")
    @JoinColumn
    private Carrera carrera;

    @Column
    private LocalDate fechaInscripcion;

    @Column
    private Integer antiguedad;

    @Column
    private Boolean graduado;

    public EstudianteCarrera() {}

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, LocalDate fechaInscripcion, int antiguedad) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.fechaInscripcion = fechaInscripcion;
        this.antiguedad = antiguedad;
        this.graduado = graduado;
        this.id = new EstudianteCarreraId(estudiante.getId(), carrera.getIdCarrera());
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

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Boolean getGraduado() {
        return graduado;
    }

    public void setGraduado(Boolean graduado) {
        this.graduado = graduado;
    }


}
