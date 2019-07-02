package com.ilyaskrypnik.pane.service;

import com.ilyaskrypnik.pane.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    private final EntityManager em;

    public StudentService() {
        em = Persistence.createEntityManagerFactory("Student").createEntityManager();
    }

    public Student createStudent(Student student) {
        return persist(student);
    }

    public void delete(long id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Student get(long id){
        return em.find(Student.class, id);
    }

    public Student update(Student student){
        return persist(student);
    }

    public List<Student> getAll(){
        TypedQuery<Student> namedQuery = em.createNamedQuery("Student.getAll", Student.class);
        return namedQuery.getResultList();
    }

    private Student persist(Student student) {
        Student studentFromDb = null;
        try {
            em.getTransaction().begin();
            studentFromDb = em.merge(student);
            em.getTransaction().commit();
        } catch (IllegalStateException e) {
            LOG.error("Unable to begin transaction.", e);
        } catch (IllegalArgumentException e) {
            LOG.error("An error occurred during saving Student entity.", e);
        } catch (TransactionRequiredException e) {
            LOG.error("There is no transaction while creating Student entity.", e);
        } catch (RollbackException e) {
            LOG.error("An error occurred during commit.", e);
            try {
                em.getTransaction().rollback();
            } catch (IllegalStateException exception) {
                LOG.error("Unable to rollback transaction.", exception);
            } catch (PersistenceException exception) {
                LOG.error("Unexpected exception during rollback.", exception);
            }
        }
        return studentFromDb;
    }
}
