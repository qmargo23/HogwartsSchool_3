package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
//    private final StudentService out = new StudentService(studentRepository);
//
//    @Test
//    public void shouldReturnCorrectResult_createStudent() {
//        Student expected = new Student(1L, "Harry", 12);
//        Student actual = out.createStudent(new Student(1L, "Harry", 12));
//        assertNotNull(actual);
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void forTestFindBiIdStudent() {
//        Student expected = new Student(1L, "Harry", 12);
//        out.createStudent(new Student(1L, "Harry", 12));
//        Student actual = out.findStudent(1L);
//
//        assertNotNull(actual.getId());
//        assertEquals(expected.getId(), actual.getId());
//    }
//
//    @Test
//    public void forTestEditStudent() {
//        Student harry = new Student(1L, "harry", 12);
//        int expected = harry.getAge();
//
//        Student actualHarry = new Student(1L, "harry", 13);
//        out.editStudent(actualHarry);
//        actualHarry.setAge(12);
//        int actual = actualHarry.getAge();
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testFor_deleteStudent() {
//        Student harry = new Student(1L, "Harry", 12);
//
//        out.createStudent(harry);
//        Long id = harry.getId();
//
//        out.deleteStudent(1L);
//        harry.setId(null);
//        Long actual = harry.getId();
//
//        assertNull(actual);
//        assertNotSame(id, null);
//    }
//
//    @Test
//    public void findByAge() {
//        Collection<Student> expected = new ArrayList<>();
//        expected.add(new Student(1L, "Harry", 12));
//
//        Student student1 = new Student(1L, "Harry", 12);
//        Student student2 = new Student(2L, "Harry2", 22);
//        Student student3 = new Student(3L, "Harry3", 13);
//        Student student4 = new Student(4L, "Harry4", 14);
//        out.createStudent(student1);
//        out.createStudent(student2);
//        out.createStudent(student3);
//        out.createStudent(student4);
//        Collection<Student> actual = out.findByAge(12);
//
//        assertEquals(expected, actual);
//    }
}