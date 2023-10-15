package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentServiceInter {
    Student createStudent(Student student);



    Student findStudent(Long id);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    public Faculty getFacultyByStudentId(Long id);

    List<Student> getByFacultyId(Long facultyId);

    Student editStudent(Student student);

    void deleteStudent(Long id);
}