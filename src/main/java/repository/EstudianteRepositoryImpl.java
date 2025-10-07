package repository;

import dto.EstudianteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Estudiante;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository{

    public List<EstudianteDTO> buscarPorGenero(String genero) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = em.createQuery(
                        "SELECT new dto.EstudianteDTO(" +
                                "  e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.libretaUniversitaria" +
                                ") " +
                                "FROM Estudiante e " +
                                "WHERE e.genero = :genero ",
                        EstudianteDTO.class)
                .setParameter("genero", genero)
                .getResultList();
        em.close();
        return estudiantes;
    }

    @Override
    public EstudianteDTO buscarPorLibreta(Integer libreta) {
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO dto = em.createQuery(
                        "SELECT new dto.EstudianteDTO(" +
                                "  e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.libretaUniversitaria" +
                                ") " +
                                "FROM Estudiante e " +
                                "WHERE e.libretaUniversitaria = :libreta",
                        EstudianteDTO.class)
                .setParameter("libreta", libreta)
                .getSingleResult();
        em.close();
        return dto;
    }


    @Override
    public List<Estudiante> getEstudiantes() {   //recuperar todos los estudiantes ordenados por apellido
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e ORDER BY e.apellido ASC", Estudiante.class).getResultList();
        em.close();
        return estudiantes;
    }

    @Override
    public void insert(Estudiante estudiante) {   //dar de alta un estudiante
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
            System.out.println("Estudiante dado de alta correctamente: " + estudiante);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void cargarDesdeCSV() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Long count = em.createQuery("SELECT COUNT(e) FROM Estudiante e", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("La tabla Estudiante ya contiene datos. No se cargará el CSV.");
                em.getTransaction().commit();
                return;
            }
            em.getTransaction().commit();

            Reader in = new InputStreamReader(
                    new FileInputStream("src/main/resources/estudiantes.csv"),
                    java.nio.charset.StandardCharsets.ISO_8859_1
            );
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(in);

            em.getTransaction().begin();

            for (CSVRecord row : parser) {
                Long dni = Long.parseLong(row.get("DNI"));
                String nombre = row.get("nombre");
                String apellido = row.get("apellido");
                Integer edad = Integer.parseInt(row.get("edad"));
                String genero = row.get("genero");
                String ciudad = row.get("ciudad");
                Integer lu = Integer.parseInt(row.get("LU"));

                Estudiante e = new Estudiante();
                e.setNumeroDocumento(dni);          // PK
                e.setNombre(nombre);
                e.setApellido(apellido);
                e.setEdad(edad);
                e.setGenero(genero);
                e.setCiudadResidencia(ciudad);
                e.setLibretaUniversitaria(lu);

                em.persist(e);
            }

            em.getTransaction().commit();
            parser.close();
            in.close();

            System.out.println("Estudiantes cargados exitosamente desde CSV.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


}
