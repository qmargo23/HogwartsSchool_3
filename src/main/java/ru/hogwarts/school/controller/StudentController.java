package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();//return 404
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Object> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();//return 404
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();//return 404
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/by-age-between")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        if (max > min) {
            return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/faculty-by-student-id")
    public ResponseEntity<Faculty> getFacultyByStudentId(@RequestParam Long id) {
        Faculty facultyByStudentId = studentService.getFacultyByStudentId(id);
        if (facultyByStudentId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyByStudentId);
    }
}
