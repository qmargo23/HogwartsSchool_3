package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentLastFive;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int min, int max);
    List<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountStudent();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double getAvgAgeStudent();

    @Query(value = "SELECT * FROM student ORDER BY id desc LIMIT 5", nativeQuery = true)
    List<StudentLastFive> getLastFiveStudent();

}
