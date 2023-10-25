package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {//create-POST
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {//read-GET
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {//update-PUT
        Student studentFromDb = findStudent(student.getId());

        if (studentFromDb == null) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {//delete-DELETE
        studentRepository.deleteById(id);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentId(Long id) {
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> getByFacultyId(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }
}
