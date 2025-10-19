package org.example.tp3.dto;

public class CarreraReporteDTO {
    private Long idCarrera;
    private String carrera;
    private Integer anio;
    private Long inscriptos;
    private Long egresados;

    public CarreraReporteDTO(Long idCarrera, String carrera, Integer anio, Long inscriptos, Long egresados) {
        this.idCarrera = idCarrera;
        this.carrera = carrera;
        this.anio = anio;
        this.inscriptos = inscriptos;
        this.egresados = egresados;
    }

    public Long getIdCarrera() {
        return idCarrera;

    }
    public String getCarrera() {
        return carrera;
    }

    public Integer getAnio() {
        return anio;
    }

    public Long getInscriptos() {
        return inscriptos;
    }

    public Long getEgresados() {
        return egresados;
    }

    @Override
    public String toString() {
        return carrera + " (" + anio + ") -> insc=" + inscriptos + ", egr=" + egresados;
    }

}
