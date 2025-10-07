package repository;

import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;
import modelo.Estudiante;
import modelo.EstudianteCarrera;
import modelo.EstudianteCarreraId;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import dto.CarreraConInscriptosDTO;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class EstudianteCarreraRepositoryImpl implements EstudianteCarreraRepository {

    @Override
    public void insert(Long numeroDocumento, Long idCarrera, Integer inscripcion, Integer graduacion, Integer antiguedad) {   //matricular un estudiante en una carrera
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Estudiante estudiante = em.find(Estudiante.class, numeroDocumento);
            if (estudiante == null) {
                throw new IllegalArgumentException("Estudiante con DNI " + numeroDocumento + " no existe");
            }
            Carrera carrera = em.find(Carrera.class, idCarrera);
            if (carrera == null) {
                throw new IllegalArgumentException("Carrera con id " + idCarrera + " no existe");
            }

            EstudianteCarreraId pk = new EstudianteCarreraId(numeroDocumento, idCarrera);
            EstudianteCarrera existente = em.find(EstudianteCarrera.class, pk);

            if (existente == null) {
                EstudianteCarrera ec = new EstudianteCarrera();
                ec.setId(pk);
                ec.setEstudiante(estudiante);
                ec.setCarrera(carrera);
                ec.setInscripcion(inscripcion);
                ec.setGraduacion(graduacion);
                ec.setAntiguedad(antiguedad);

                em.persist(ec);
            } else {
                System.out.println("El estudiante ya estaba matriculado en esa carrera, no se modificó.");
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void cargarDesdeCSV() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Long count = em.createQuery("SELECT COUNT(ec) FROM EstudianteCarrera ec", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("La tabla EstudianteCarrera ya contiene datos. No se cargará el CSV.");
                em.getTransaction().commit();
                return;
            }
            em.getTransaction().commit();

            Reader in = new FileReader("src/main/resources/estudianteCarrera.csv");
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(in);

            em.getTransaction().begin();

            for (CSVRecord row : parser) {
                Long idEstudiante = Long.parseLong(row.get("id_estudiante"));
                Long idCarrera = Long.parseLong(row.get("id_carrera"));
                Integer inscripcion = Integer.parseInt(row.get("inscripcion"));
                Integer graduacion = Integer.parseInt(row.get("graduacion"));
                Integer antiguedad = Integer.parseInt(row.get("antiguedad"));

                EstudianteCarreraId pk = new EstudianteCarreraId(idEstudiante, idCarrera);

                // *** ÚNICO CAMBIO NECESARIO: evitar duplicado en el contexto ***
                EstudianteCarrera existente = em.find(EstudianteCarrera.class, pk);
                if (existente != null) {
                    // Si querés, podés actualizar campos acá en vez de saltar
                    // existente.setInscripcion(inscripcion);
                    // existente.setGraduacion(graduacion);
                    // existente.setAntiguedad(antiguedad);
                    continue;
                }

                Estudiante estudiante = em.find(Estudiante.class, idEstudiante);
                if (estudiante == null) {
                    System.out.println("Advertencia: Estudiante " + idEstudiante + " no encontrado. Saltando registro.");
                    continue;
                }

                Carrera carrera = em.find(Carrera.class, idCarrera);
                if (carrera == null) {
                    System.out.println("Advertencia: Carrera " + idCarrera + " no encontrada. Saltando registro.");
                    continue;
                }

                EstudianteCarrera ec = new EstudianteCarrera();
                ec.setId(pk);
                ec.setEstudiante(estudiante);
                ec.setCarrera(carrera);
                ec.setInscripcion(inscripcion);
                ec.setGraduacion(graduacion);
                ec.setAntiguedad(antiguedad);

                em.persist(ec);
            }

            em.getTransaction().commit();
            parser.close();
            in.close();

            System.out.println("EstudianteCarrera cargadas exitosamente desde CSV.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CarreraConInscriptosDTO> getCarrerasConCantidadInscriptos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT new dto.CarreraConInscriptosDTO(c.idCarrera, c.carrera, COUNT(ec.id.estudianteId)) " +
                    "FROM Carrera c " +
                    "JOIN EstudianteCarrera ec ON c.idCarrera = ec.id.carreraId " +
                    "GROUP BY c.idCarrera, c.carrera " +
                    "ORDER BY COUNT(ec.id.estudianteId) DESC";

            return em.createQuery(jpql, CarreraConInscriptosDTO.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
