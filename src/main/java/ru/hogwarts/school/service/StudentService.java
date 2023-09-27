package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {
    //private final Map<Long, Student> students = new HashMap<>();
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {//tests!!!
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {//create-POST
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {//read-GET
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {//update-PUT
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {//delete-DELETE
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        return getStudents().stream()
                .filter(e -> e.getAge() == age)
                .collect(Collectors.toList());
    }
}
