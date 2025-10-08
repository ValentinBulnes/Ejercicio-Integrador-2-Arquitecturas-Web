package dto;

public class CarreraReporteDTO {
    private String carrera;
    private Integer anio;
    private Long cantidad;
    private String tipo; // "Inscriptos" o "Egresados"

    public CarreraReporteDTO(String carrera, Integer anio, Long cantidad, String tipo) {
        this.carrera = carrera;
        this.anio = anio;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public String getCarrera() { return carrera; }
    public Integer getAnio() { return anio; }
    public Long getCantidad() { return cantidad; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        return carrera + " | " + anio + " | " + tipo + ": " + cantidad;
    }
}
