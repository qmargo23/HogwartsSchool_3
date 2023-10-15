package ru.hogwarts.school.controller;

import org.springframework.http.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Student;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void createStudentSuccess() {
        //Подготовка входных данных
        Student studentForCreate = new Student("test", 1);

        //Подготовка ожидаемого результата
        Student expectedStudent = new Student("test", 1);

        //Начало теста
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForCreate, Student.class);
        assertThat(postedStudent).isNotNull();

        assertEquals(expectedStudent.getName(), postedStudent.getName());
        assertEquals(expectedStudent.getAge(), postedStudent.getAge());

        studentController.deleteStudent(postedStudent.getId());
    }

    @Test
    public void deleteStudentSuccess() {
        //Подготовка входных данных
        Student studentForDelete = new Student("test2", 2);

        //Начало теста
        Student postedStudent = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentForDelete, Student.class);
        this.restTemplate.delete("http://localhost:" + port + "/student/" + postedStudent.getId());

        //проверяем, что такого студента после удаления нет в базе
        Optional<Student> studentOpt = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + postedStudent.getId(), Optional.class);
        assertThat(studentOpt).isNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/2", String.class))
                .isNotEmpty();
    }

    @Test
    public void testGetFacultyByStudentId() {
        Long facultyId = 1L;

        // Построение URL с параметром запроса
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/faculty/students-by-faculty-id")
                .queryParam("id", facultyId);

        // Отправка GET-запроса с параметром
        ResponseEntity<Student[]> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                Student[].class);

        // Проверка статуса ответа
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}



