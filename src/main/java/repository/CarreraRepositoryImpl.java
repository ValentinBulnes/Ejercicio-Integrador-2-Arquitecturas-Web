package repository;

import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CarreraRepositoryImpl implements CarreraRepository{

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
