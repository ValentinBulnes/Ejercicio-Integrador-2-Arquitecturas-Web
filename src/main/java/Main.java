import dto.CarreraConInscriptosDTO;
import dto.EstudianteDTO;
import modelo.Carrera;
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

        for(Estudiante estudiante : er.getEstudiantes()) {   //buscar todos los estudiantes ordenados por apellido
            System.out.println(estudiante);
        }

        System.out.println(er.buscarPorLibreta(34978));   //buscar estudiante por libreta

        for (EstudianteDTO estudianteDTO : er.buscarPorGenero("Female")) {  //buscar todos los estudiantes en base a su genero
            System.out.println(estudianteDTO);
        }

        for (CarreraConInscriptosDTO carreraDTO : ecr.getCarrerasConCantidadInscriptos()) {  //buscar carreras con cantidad de inscriptos
            System.out.println(carreraDTO);
        }
    }
}
