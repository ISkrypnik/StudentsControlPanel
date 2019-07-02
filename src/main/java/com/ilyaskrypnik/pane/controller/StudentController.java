package com.ilyaskrypnik.pane.controller;

import com.ilyaskrypnik.pane.domain.Student;
import com.ilyaskrypnik.pane.service.StudentService;

import java.util.List;

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public Student createNewStudent(Student student) {
        return studentService.createStudent(student);
    }

    public void deleteStudent(long id) {
        studentService.delete(id);
    }

    public void updateStudent(Student student) {
        studentService.update(student);
    }

    public Student getStudent(long id) {
        return studentService.get(id);
    }

    public List<Student> getAllStudents() {
        return studentService.getAll();
    }
}
