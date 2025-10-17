package repository;

import dto.CarreraReporteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
    public List<CarreraReporteDTO> reporteCarreras() {
        String sql =
                "SELECT t.id_carrera, t.carrera, t.anio, " +
                        "       SUM(t.inscriptos) AS inscriptos, SUM(t.egresados) AS egresados " +
                        "FROM ( " +
                        "  SELECT c.idCarrera AS id_carrera, c.carrera, ec.inscripcion AS anio, COUNT(*) AS inscriptos, 0 AS egresados " +
                        "  FROM `estudianteCarrera` ec " +
                        "  JOIN `carrera` c ON c.idCarrera = ec.`carrera_id` " +
                        "  WHERE ec.inscripcion IS NOT NULL " +
                        "  GROUP BY c.idCarrera, c.carrera, ec.inscripcion " +
                        "  UNION ALL " +
                        "  SELECT c.idCarrera AS id_carrera, c.carrera, ec.graduacion AS anio, 0 AS inscriptos, COUNT(*) AS egresados " +
                        "  FROM `estudianteCarrera` ec " +
                        "  JOIN `carrera` c ON c.idCarrera = ec.`carrera_id` " +
                        "  WHERE ec.graduacion IS NOT NULL AND ec.graduacion <> 0 " +
                        "  GROUP BY c.idCarrera, c.carrera, ec.graduacion " +
                        ") t " +
                        "GROUP BY t.id_carrera, t.carrera, t.anio " +
                        "ORDER BY t.carrera ASC, t.anio ASC";

        EntityManager em = JPAUtil.getEntityManager();
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> rows = em.createNativeQuery(sql).getResultList();

            List<CarreraReporteDTO> out = new ArrayList<>();
            for (Object[] r : rows) {
                Long idCarrera       = ((Number) r[0]).longValue();
                String carrera       = (String) r[1];
                Integer anio         = ((Number) r[2]).intValue();
                Long inscriptos      = ((Number) r[3]).longValue();
                Long egresados       = ((Number) r[4]).longValue();
                out.add(new CarreraReporteDTO(idCarrera, carrera, anio, inscriptos, egresados));
            }
            return out;
        } finally {
            em.close();
        }
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
