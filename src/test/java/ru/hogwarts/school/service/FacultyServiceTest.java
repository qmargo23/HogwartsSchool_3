package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {
//    @Autowired
//    FacultyService facultyService;
//    @Autowired
//    FacultyRepository facultyRepository;
//private final FacultyService out = new FacultyService(facultyRepository);
//    @Test
//    void addFaculty() {
//        Faculty expected = new  Faculty(1L, "Faculty1", "red");
//
//        Faculty actual = out.addFaculty(new Faculty(1L,"Faculty1","red"));
//        assertNotNull(actual);
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findFaculty() {
//        Faculty expected = new Faculty(1L, "Faculty1", "red");
//        out.addFaculty(new Faculty(1L, "Faculty1", "red"));
//        Faculty actual = out.findFaculty(1L);
//
//        assertNotNull(actual.getId());
//        assertEquals(expected.getId(), actual.getId());
//    }
//
//    @Test
//    void editFaculty() {
//        Faculty faculty1 = new Faculty(1L, "Faculty1", "red");
//        long expected = faculty1.getId();
//
//        Faculty actualFaculty1 = new Faculty(1L, "Faculty1", "red");
//        out.editFaculty(actualFaculty1);
//        actualFaculty1.setId(1L);
//        long actual = actualFaculty1.getId();
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void deleteFaculty() {
//        Faculty faculty1 = new Faculty(1L, "Faculty1", "red");
//
//        out.addFaculty(faculty1);
//        Long id = faculty1.getId();
//
//        out.deleteFaculty(1L);
//        faculty1.setId(null);
//        Long actual = faculty1.getId();
//
//        assertNull(actual);
//        assertNotSame(id, null);
//    }
//
//    @Test
//    void findByColor() {
//        Collection<Faculty> expected = new ArrayList<>();
//        expected.add(new Faculty(1L, "Faculty1", "red"));
//
//        Faculty faculty1 = new Faculty(1L, "Faculty1", "red");
//        Faculty faculty2 = new Faculty(1L, "Faculty1", "green");
//        Faculty faculty3 = new Faculty(1L, "Faculty1", "red2");
//        Faculty faculty4 = new Faculty(1L, "Faculty1", "34red");
//        Faculty faculty5 = new Faculty(1L, "Faculty1", "red56");
//
//        out.addFaculty(faculty1);
//        out.addFaculty(faculty2);
//        out.addFaculty(faculty3);
//        out.addFaculty(faculty4);
//        out.addFaculty(faculty5);
//
//        Collection<Faculty> actual = out.findByColor("red");
//
//        assertEquals(expected, actual);
//    }
}