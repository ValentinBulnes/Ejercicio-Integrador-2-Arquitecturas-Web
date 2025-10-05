package Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Estudiante {
    @Id
    private int id;
    private String nombre;
}
