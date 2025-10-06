package Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable /// le dice a JPA que esta clase no es una entidad, sino una estructura que se puede incrustar dentro de otra entidad (la que usará @EmbeddedId)
public class EstudianteCarreraId implements Serializable { /// - implements Serializable - es obligatorio, porque JPA necesita poder serializar (guardar/cargar) los IDs compuestos.

    @Column(name = "estudiante_id")
    private Long estudianteId;

    @Column(name = "carrera_id")
    private Long carreraId;

    // Constructores
    public EstudianteCarreraId() {}

    public EstudianteCarreraId(Long estudianteId, Long carreraId) {
        this.estudianteId = estudianteId;
        this.carreraId = carreraId;
    }

    // Getters y Setters
    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }


}
