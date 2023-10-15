package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id){
        Optional<Faculty> faculty = facultyService.findFaculty(id);
        if (faculty.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty.get());
    }
    @GetMapping()
    public ResponseEntity<List<Faculty>>getFacultyByColorInfo(@RequestParam ("color")
                                                              String color) {
        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }
    @GetMapping("/get-color-or-name")
    public ResponseEntity<Set<Faculty>> getFacultyByColorOrName(@RequestParam("str") String str){
        return ResponseEntity.ok( facultyService.getFacultyByColorOrByName(str));
    }
    @GetMapping("/get-student-by-faculty-id")
    public ResponseEntity<List<Student> >getStudentByFacultyId(@RequestParam("id") Long id){
        return ResponseEntity.ok(facultyService.getStudentByFacultyId(id));
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }
    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty){
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

}