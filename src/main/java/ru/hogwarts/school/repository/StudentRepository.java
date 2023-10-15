package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;


import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    public List<Student> getAllByAge(int age);
    public List<Student> findAllByAgeBetween(int min,int max);
    public List<Student> findByFacultyId(Long facultyId);
}
