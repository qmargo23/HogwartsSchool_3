package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FacultyServiceInter {
    Faculty createFaculty(Faculty faculty);

    List<Faculty> getFacultyByColor(String color);


    Set<Faculty> getFacultyByColorOrByName(String str);

    List<Student> getStudentByFacultyId(Long id);

    Optional<Faculty> findFaculty(Long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(Long id);
}
