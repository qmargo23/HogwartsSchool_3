package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }
    @Test
    void addFacultySuccess() {
        //Подготовка входных данных
        Faculty addFaculty = new Faculty("Name", "1");

        //Подготовка ожидаемого результата
        Faculty expectedFaculty = new Faculty("Name", "1");

        //Начало теста
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", addFaculty, Faculty.class);
        assertThat(postedFaculty).isNotNull();

        assertEquals(expectedFaculty.getName(), postedFaculty.getName());
        assertEquals(expectedFaculty.getColor(), postedFaculty.getColor());

        facultyController.deleteFaculty(postedFaculty.getId());
    }

    @Test
    public void testGetFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class))
                .isNotNull();
    }

    @Test
    void deleteFaculty() {
        //Подготовка входных данных
        Faculty facultyForDelete = new Faculty("Name2", "2");

        //Начало теста
        Faculty postedFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/student", facultyForDelete, Faculty.class);
        this.restTemplate.delete("http://localhost:" + port + "/student/" + postedFaculty.getId());

        //проверяем, что такого факультета после удаления нет в базе
        Optional<Faculty> facultyOpt = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + postedFaculty.getId(), Optional.class);
        assertThat(facultyOpt).isNull();
    }

    @Test
    public void testFindFacultyNameOrColor() {
        String param = "Слизерин";

        // Создание HttpEntity
        HttpEntity<Void> httpEntity = new HttpEntity<>(null);

        // Отправка GET-запроса с параметрами
        ResponseEntity<Faculty[]> response = restTemplate.exchange("http://localhost:" + port + "/faculty//by-name-or-color?param=" + param,
                HttpMethod.GET,
                httpEntity,
                Faculty[].class);

        // Проверка статуса ответа
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testGetStudentByFacultyId() {
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