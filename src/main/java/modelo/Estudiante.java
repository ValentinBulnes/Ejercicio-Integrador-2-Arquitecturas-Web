package modelo;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "estudiante")
public class Estudiante {
    @Id
    @Column(name = "numero_documento", nullable = false)
    private Long numeroDocumento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private String genero;

    @Column(name = "ciudad_residencia")
    private String ciudadResidencia;

    @Column(name = "libreta_universitaria", nullable = false, unique = true)
    private Integer libretaUniversitaria;

    public Estudiante() {}

    public Estudiante(String nombre, String apellido, String genero, Long numeroDocumento, String ciudadResidencia, Integer libretaUniversitaria, String ciudad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.numeroDocumento = numeroDocumento;
        this.ciudadResidencia = ciudadResidencia;
        this.libretaUniversitaria = libretaUniversitaria;
    }

    public Long getNumeroDocumentoId() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public Integer getLibretaUniversitaria() {
        return libretaUniversitaria;
    }

    public void setLibretaUniversitaria(Integer libretaUniversitaria) {
        this.libretaUniversitaria = libretaUniversitaria;
    }

    public Long getId() {
        return numeroDocumento;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "numeroDocumento=" + numeroDocumento + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", genero=" + genero + ", ciudadResidencia=" + ciudadResidencia + ", libretaUniversitaria=" + libretaUniversitaria + '}';
    }
}
