package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    // запись в БД появляется, но с непонятно места id, от чего это зависит?
    @PostMapping//POST
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    //  почему не видит данные из БД, не могу получить ранее созданные. только последний 12
    @GetMapping("{id}")//GET
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
//создается новая строка что плохо как убрать через проверку наличия ID?

    @PutMapping//PUT
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
    // ошибка 500, как ее избежать?
    //
    //org.springframework.dao.EmptyResultDataAccessException: No class ru.hogwarts.school.model.Faculty entity with id 0 exists!
    @DeleteMapping("{id}")//DELETE
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findFaculty-by-name-or-color")
    ResponseEntity<List<Faculty>> findByNameOrColorIgnoreCase(@RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "color", required = false) String color) {
        List<Faculty> res = facultyService.findByNameOrColorIgnoreCase(name, color);
        if (res.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }
    @GetMapping("/students-by-faculty-id")
    public List<Student> getStudentsByFacultyId(@RequestParam Long id) {
        return facultyService.getStudentsByFacultyId(id);
    }
}
