package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    FacultyController facultyController;
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        // проверяем загрузку контроллера
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void addFaculty() throws Exception {
        Faculty facultyTest = new Faculty();
        facultyTest.setId(1L);
        facultyTest.setName("Test1");
        facultyTest.setColor("color1");

        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Faculty faculty = new ObjectMapper().readValue(response, Faculty.class);

        Assertions.assertThat(faculty.getId()).isNotNull();
        Assertions.assertThat(faculty.getName()).isEqualTo(facultyTest.getName());
        Assertions.assertThat(faculty.getColor()).isEqualTo(facultyTest.getColor());
        restTemplate.delete("http://localhost:" + port + "/faculty/1");
    }

    @Test
    void getFacultyInfo() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test2", "Color2");

        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Faculty faculty = new ObjectMapper().readValue(response, Faculty.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotEmpty();
        Assertions.assertThatObject(faculty).isEqualTo(facultyTest);
        restTemplate.delete("http://localhost:" + port + "/faculty/1");
    }

    //todo: по-отдельности этот тест проходит, но если запускать все вместе со всеми-  падает... помогите это исправить
    @Test
    void editFaculty() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test3", "Color3");

        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Faculty faculty = new ObjectMapper().readValue(response, Faculty.class);

        facultyTest.setName("Test1");
        facultyTest.setColor("Color1");

        System.out.println(facultyTest);
        //Faculty{id=1, name='Test1', color='Color1'}

        restTemplate.put("http://localhost:" + port + "/faculty", facultyTest, String.class);
        String responseEdit = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), String.class);
        Faculty facultyEdit = new ObjectMapper().readValue(responseEdit, Faculty.class);

        System.out.println(responseEdit);
        //{"id":1,"name":"Test1","color":"Color1"}

        Assertions
                .assertThat(responseEdit).isNotEmpty();
        Assertions.assertThat(facultyEdit.getName()).isEqualTo(facultyTest.getName());
        Assertions.assertThat(facultyEdit.getColor()).isEqualTo(facultyTest.getColor());
//
        restTemplate.delete("http://localhost:" + port + "/faculty/1");
    }

    @Test
    void deleteFaculty() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test3", "Color3");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Faculty faculty = new ObjectMapper().readValue(response, Faculty.class);

        restTemplate.delete("http://localhost:" + port + "/faculty/" + faculty.getId());
        String responseEdit = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), String.class);

        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(responseEdit).isNull();
    }

    @Test
    void findByNameOrColorIgnoreCase() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test1", "Color1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);

        String responseFind = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/findFaculty-by-name-or-color?name=Test1", String.class);

        Assertions
                .assertThat(responseFind).isEqualTo("[" + response + "]");
        restTemplate.delete("http://localhost:" + port + "/faculty/1");
    }

    //todo: по-отдельности этот тест проходит, но если запускать все вместе со всеми-  падает... помогите это исправить
    //Нарушение ссылочной целостности:
    @Test
    void getStudentsByFacultyId() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test4", "Color4");
        String facultyResponse = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Student studentTest = new Student(1L, "Test1", 10);
        studentTest.setFaculty(facultyTest);
        String studentResponse = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class);

        String responseGetStudent = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/students-by-faculty-id?id=1", String.class);

        Assertions.assertThat(responseGetStudent).isEqualTo("[" + studentResponse + "]");
    }
}