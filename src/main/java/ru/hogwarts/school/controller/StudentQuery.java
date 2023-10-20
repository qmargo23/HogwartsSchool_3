package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.StudentLastFive;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/studentQuery")
public class StudentQuery {
    private final StudentService studentService;

    public StudentQuery(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("student-count")
    public Integer getStudentCount() {
        return studentService.getCount();
    }

    @GetMapping("avg-student")
    public Double getAvgAge() {
        return studentService.getAvgAgeStudent();
    }

    @GetMapping("student-last-five")
    public List<StudentLastFive> getLastFives() {
        return studentService.getLastFives();
    }
}
