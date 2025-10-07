package dto;

public class EstudianteDTO {
    private String nombre;
    private String apellido;
    private Integer edad;
    private String ciudadResidencia;
    private Integer libretaUniversitaria;

    public EstudianteDTO() {}

    public EstudianteDTO(String nombre, String apellido, Integer edad, String ciudadResidencia, Integer libretaUniversitaria) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.ciudadResidencia = ciudadResidencia;
        this.libretaUniversitaria = libretaUniversitaria;
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
    @Override
    public String toString() {
        return "EstudianteDTO{" + "nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", ciudadResidencia=" + ciudadResidencia + ", libretaUniversitaria=" + libretaUniversitaria + '}';
    }
}
