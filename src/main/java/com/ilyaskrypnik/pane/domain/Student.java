package com.ilyaskrypnik.pane.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NamedQuery(name = "Student.getAll", query = "SELECT s from Student s")
@Data
@NoArgsConstructor
public class Student {

    public Student(String firstName, String lastName, long groupNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNumber = groupNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private long groupNumber;
}
