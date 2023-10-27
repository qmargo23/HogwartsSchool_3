package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {//create-POST
        logger.info("Was invoked method for createStudent");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {//read-GET
        logger.info("Was invoked method for findStudent");
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {//update-PUT
        logger.info("Was invoked method for editStudent");
        Student studentFromDb = findStudent(student.getId());

        if (studentFromDb == null) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {//delete-DELETE
        logger.info("Was invoked method for deleteStudent");
        studentRepository.deleteById(id);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for findByAgeBetween");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentId(Long id) {
        logger.info("Was invoked method for getFacultyByStudentId");
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> getByFacultyId(Long facultyId) {
        logger.info("Was invoked method for getByFacultyId");
        return studentRepository.findByFacultyId(facultyId);
    }
}