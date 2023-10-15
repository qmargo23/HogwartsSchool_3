package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    public List<Faculty> getAllByColor(String color);
    public List<Faculty> getAllByColorIgnoreCase(String color);
    public List<Faculty> getAllByNameIgnoreCase(String name);

}
