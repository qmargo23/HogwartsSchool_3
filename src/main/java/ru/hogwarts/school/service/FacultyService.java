package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
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
    public Collection<Faculty> getFaculty() {
        return facultyRepository.findAll();
    }
    public List<Faculty> findByColor(String color) {
        return getFaculty().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
