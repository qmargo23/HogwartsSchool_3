package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }

    public Faculty addFaculty(Faculty faculty) {//create-POST
        logger.info("Was invoked method for addFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {//read-GET
        logger.info("Was invoked method for findFaculty");
        if (facultyRepository.findById(id).isEmpty()) {
            return null;
        }
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {//update-PUT
        logger.info("Was invoked method for editFaculty");
        Faculty fromDB = findFaculty(faculty.getId());
        if (fromDB == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {//delete-DELETE
        logger.info("Was invoked method for deleteFaculty");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findByNameOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for findByNameOrColorIgnoreCase");
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }

    public List<Student> getStudentsByFacultyId(Long id) {
        logger.info("Was invoked method for getStudentsByFacultyId");
        return studentService.getByFacultyId(id);
    }

    public String getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }
}
