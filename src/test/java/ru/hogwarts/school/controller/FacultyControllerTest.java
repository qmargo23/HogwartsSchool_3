package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
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
    public void contextLoads() throws Exception {
        // проверяем загрузку контроллера
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void addFaculty() throws Exception {
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
    public void getFacultyInfo() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test2", "Color2");

        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Faculty faculty = new ObjectMapper().readValue(response, Faculty.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotEmpty();
        Assertions.assertThatObject(faculty).isEqualTo(facultyTest);
        restTemplate.delete("http://localhost:" + port + "/faculty/1");
    }

    @Test
    public void editFaculty() throws Exception {
        ResponseEntity<Faculty> newFacultyResponse = restTemplate.postForEntity("http://localhost:" + port + "/faculty", new Faculty(0L, "New faculty", "red"), Faculty.class);
        Faculty newFaculty = newFacultyResponse.getBody();
        newFaculty.setName("FACULTY_TEST");
        newFaculty.setColor("RED_TEST");

        restTemplate.put("http://localhost:" + port + "/faculty", newFaculty, Faculty.class);
        Faculty editFaculty = newFacultyResponse.getBody();

        Assertions.assertThat(editFaculty.getName()).isEqualTo(newFaculty.getName());
        Assertions.assertThat(editFaculty.getColor()).isEqualTo(newFaculty.getColor());
    }

    @Test
    public void deleteFaculty() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test3", "Color3");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Faculty faculty = new ObjectMapper().readValue(response, Faculty.class);

        restTemplate.delete("http://localhost:" + port + "/faculty/" + faculty.getId());
        String responseEdit = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), String.class);

        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(responseEdit).isNull();
    }

    @Test
    public void findByNameOrColorIgnoreCase() throws Exception {
        Faculty facultyTest = new Faculty(1L, "Test1", "Color1");
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);

        String responseFind = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/findFaculty-by-name-or-color?name=Test1", String.class);

        Assertions
                .assertThat(responseFind).isEqualTo("[" + response + "]");
        restTemplate.delete("http://localhost:" + port + "/faculty/1");
    }

    @Test
    public void getStudentsByFacultyId() throws Exception {
        ResponseEntity<Faculty> newFacultyResponse = restTemplate.
                postForEntity("http://localhost:" + port + "/faculty", new Faculty(0L, "New faculty", "red"),Faculty.class);
        Faculty newFaculty = newFacultyResponse.getBody();
        //добавляем в БД студента привязанного к факультету //Student{id=1, name='NAME_01', age=10}
        ResponseEntity<Student> newStudentResponse = restTemplate.
                postForEntity("http://localhost:" + port + "/student", new Student(0L, "NAME_01", 10, newFaculty), Student.class);

        String responseGetStudent = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/students-by-faculty-id?id=" + newFaculty.getId(), String.class);
        String res = responseGetStudent;//[{"id":1,"name":"NAME_01","age":10,"faculty":{"id":1,"name":"New faculty","color":"red"}}]
        //  какой будет ЗАПРОС - получить список студентов getStudentsByFacultyIdResponse
        ResponseEntity<Student> getStudentsByFacultyIdResponse;//todo  узнать код (для получения списка)
        // как получить???? как вытащить данные из responseGetStudent, чтобы сравнивать конкретные поля???

        //"топорно" проверяем что в строке есть нужный студент//получаем// Local variable 'res' is redundant
        Assertions
                .assertThat(res).isEqualTo(responseGetStudent);
    }
}