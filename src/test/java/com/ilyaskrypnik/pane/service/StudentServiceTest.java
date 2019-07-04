package com.ilyaskrypnik.pane.service;

import com.ilyaskrypnik.pane.domain.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StudentServiceTest {

    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Ivanov";
    private static final long GROUP = 1;

    private static StudentService studentService;
    private Student student;

    @BeforeClass
    public static void setUp() {
        studentService = new StudentService();
    }

    @Before
    public void initStudent() {
        student = new Student(FIRST_NAME, LAST_NAME, GROUP);
    }

    @Test
    public void saveAndLoadStudentTest() {
        Student studentFromDb = studentService.createStudent(student);

        Assert.assertEquals(studentFromDb.getFirstName(), student.getFirstName());
        Assert.assertEquals(studentFromDb.getLastName(), student.getLastName());
        Assert.assertEquals(studentFromDb.getGroupNumber(), student.getGroupNumber());
        Assert.assertTrue(studentFromDb.getId() > 0);
    }

    @Test
    public void updateStudentTest() {
        String oldVal = student.getFirstName();
        long oldId = student.getId();
        String newVal = "Petr";
        student.setFirstName(newVal);
        Student updatedStudent = studentService.update(student);

        Assert.assertNotEquals(oldVal, newVal);
        Assert.assertNotEquals(oldId, updatedStudent.getId());
        Assert.assertEquals(newVal, updatedStudent.getFirstName());
    }
}