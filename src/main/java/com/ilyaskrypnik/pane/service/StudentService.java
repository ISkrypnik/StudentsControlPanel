package com.ilyaskrypnik.pane.service;

import com.ilyaskrypnik.pane.domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentService {

    private final EntityManager em;

    public StudentService() {
        em = Persistence.createEntityManagerFactory("Student").createEntityManager();
    }

    public Student createStudent(Student student) {
        em.getTransaction().begin();
        Student studentFromDb = em.merge(student);
        em.getTransaction().commit();
        return studentFromDb;
    }

    public void delete(long id){
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Student get(long id){
        return em.find(Student.class, id);
    }

    public void update(Student student){
        em.getTransaction().begin();
        em.merge(student);
        em.getTransaction().commit();
    }

    public List<Student> getAll(){
        TypedQuery<Student> namedQuery = em.createNamedQuery("Student.getAll", Student.class);
        return namedQuery.getResultList();
    }
}
