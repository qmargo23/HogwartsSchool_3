package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentService studentService;

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }

    public Faculty addFaculty(Faculty faculty) {//create-POST
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {//read-GET
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {//update-PUT
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {//delete-DELETE
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findByNameOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }

    public List<Student> getStudentsByFacultyId(Long id) {
        return studentService.getByFacultyId(id);
    }
}
