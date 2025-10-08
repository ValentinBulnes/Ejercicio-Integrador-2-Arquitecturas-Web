package repository;

import dto.CarreraReporteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository{

    @Override
    public List<CarreraReporteDTO> inscriptosPorCarreraYAnio() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT new dto.CarreraReporteDTO(" +
                            "  c.carrera, ec.inscripcion, COUNT(ec), 'Inscriptos'" +
                            ") " +
                            "FROM EstudianteCarrera ec " +
                            "JOIN ec.carrera c " +
                            "WHERE ec.inscripcion IS NOT NULL " +
                            "GROUP BY c.carrera, ec.inscripcion " +
                            "ORDER BY c.carrera ASC, ec.inscripcion ASC",
                    CarreraReporteDTO.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CarreraReporteDTO> egresadosPorCarreraYAnio() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT new dto.CarreraReporteDTO(" +
                            "  c.carrera, ec.graduacion, COUNT(ec), 'Egresados'" +
                            ") " +
                            "FROM EstudianteCarrera ec " +
                            "JOIN ec.carrera c " +
                            "WHERE ec.graduacion IS NOT NULL AND ec.graduacion <> 0 " +
                            "GROUP BY c.carrera, ec.graduacion " +
                            "ORDER BY c.carrera ASC, ec.graduacion ASC",
                    CarreraReporteDTO.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CarreraReporteDTO> reporteCarreras() {
        // Unifica inscriptos + egresados en una sola lista y la ordena
        List<CarreraReporteDTO> combinado = new ArrayList<>();
        combinado.addAll(inscriptosPorCarreraYAnio());
        combinado.addAll(egresadosPorCarreraYAnio());

        combinado.sort(
                Comparator.comparing(CarreraReporteDTO::getCarrera, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(CarreraReporteDTO::getAnio)
        );
        return combinado;
    }

    @Override
    public void cargarDesdeCSV() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Long count = em.createQuery("SELECT COUNT(c) FROM Carrera c", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("La tabla Carrera ya contiene datos. No se cargará el CSV.");
                em.getTransaction().commit();
                return;
            }
            em.getTransaction().commit();

            Reader in = new FileReader("src/main/resources/carreras.csv");
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(in);

            em.getTransaction().begin();

            for (CSVRecord row : parser) {
                Long id = Long.parseLong(row.get("id_carrera"));
                String nombre = row.get("carrera");
                Integer duracion = Integer.parseInt(row.get("duracion"));

                Carrera c = new Carrera(nombre, duracion);
                c.setIdCarrera(id); // *** usar id del CSV ***
                em.persist(c);
            }

            em.getTransaction().commit();
            parser.close();
            in.close();

            System.out.println("Carreras cargadas exitosamente desde CSV.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
