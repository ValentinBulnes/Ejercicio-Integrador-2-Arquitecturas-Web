import modelo.Estudiante;
import repository.CarreraRepositoryImpl;
import repository.EstudianteCarreraRepositoryImpl;
import repository.EstudianteRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        CarreraRepositoryImpl cr = new CarreraRepositoryImpl();
        EstudianteRepositoryImpl er = new EstudianteRepositoryImpl();
        EstudianteCarreraRepositoryImpl ecr = new EstudianteCarreraRepositoryImpl();

        cr.cargarDesdeCSV();
        er.cargarDesdeCSV();
        ecr.cargarDesdeCSV();  // Debe ejecutarse al final porque depende de Estudiante y Carrera
    }
}
