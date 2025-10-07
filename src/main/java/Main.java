import modelo.Estudiante;
import repository.CarreraRepositoryImpl;
import repository.EstudianteCarreraRepositoryImpl;
import repository.EstudianteRepositoryImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarreraRepositoryImpl cr = new CarreraRepositoryImpl();
        EstudianteRepositoryImpl er = new EstudianteRepositoryImpl();
        EstudianteCarreraRepositoryImpl ecr = new EstudianteCarreraRepositoryImpl();

        cr.cargarDesdeCSV();
        er.cargarDesdeCSV();
        ecr.cargarDesdeCSV();  // Debe ejecutarse al final porque depende de Estudiante y Carrera

        for(Estudiante estudiante : er.getEstudiantes()) {
            System.out.println(estudiante);
        }
    }
}
