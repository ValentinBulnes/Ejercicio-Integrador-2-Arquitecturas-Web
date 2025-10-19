package org.example.tp3.dto;

public class CarreraConInscriptosDTO {
    private Long idCarrera;
    private String nombre;
    private Long cantidadInscriptos;

    public CarreraConInscriptosDTO(Long idCarrera, String nombre, Long cantidadInscriptos) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    // Getters y setters
    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(Long cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    @Override
    public String toString() {
        return "CarreraConInscriptosDTO{" +
                "idCarrera=" + idCarrera +
                ", nombre='" + nombre + '\'' +
                ", cantidadInscriptos=" + cantidadInscriptos +
                '}';
    }
}
