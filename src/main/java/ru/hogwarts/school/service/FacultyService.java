package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService implements FacultyServiceInter{

    private final FacultyRepository facultyRepository;
    private final StudentService studentService;

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }

    @Override

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }
    @Override
    public List<Faculty>getFacultyByColor(String color){
        return facultyRepository.getAllByColor(color);

    }
    @Override
    public Set<Faculty>getFacultyByColorOrByName(String param){
        Set<Faculty> result = new HashSet<>();
        result.addAll(facultyRepository.getAllByColorIgnoreCase(param));
        result.addAll(facultyRepository.getAllByNameIgnoreCase(param));
        return result;


    }
    @Override
    public List<Student> getStudentByFacultyId(Long id){
        return studentService.getByFacultyId(id);

    }
    @Override
    public Optional<Faculty> findFaculty(Long id){
        return facultyRepository.findById(id);
    }
    @Override
    public Faculty editFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }
    @Override
    public void deleteFaculty(Long id){
        facultyRepository.deleteById(id);
    }


}