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
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    StudentService studentService;

    @Autowired
    TestRestTemplate restTemplate;
    //     тестируем c помощью H2
//     для этого необходимо внести зависимость
//     и прописать в application.properties нужные данные

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentService).isNotNull();
    }

    @Test
    void addStudent() throws Exception {
        //создаем нового студента
        Student studentTest = new Student();
        studentTest.setName("TEST");
        studentTest.setAge(11);

        //готовим запрос   и готовим student через ObjectMapper()
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class);
        Student student = new ObjectMapper().readValue(response, Student.class);

        //проверка
        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo(studentTest.getName());
        Assertions.assertThat(student.getAge()).isEqualTo(studentTest.getAge());
    }

    @Test
    void getStudentInfo() throws Exception {
        Student studentTest = new Student(1L, "Test1", 1);

        String response = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class);
        Student student = new ObjectMapper().readValue(response, Student.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotEmpty();
        Assertions.assertThatObject(student).isEqualTo(studentTest);
    }

    @Test
    void editStudent() throws Exception {
        Student studentTest = new Student(1L, "Test2", 2);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class);
        Student student = new ObjectMapper().readValue(response, Student.class);

        studentTest.setName("Test1");
        studentTest.setAge(1);
        restTemplate.put("http://localhost:" + port + "/student", studentTest, String.class);
        String responseEdit = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + student.getId(), String.class);
        Student studentEdit = new ObjectMapper().readValue(responseEdit, Student.class);

        Assertions
                .assertThat(responseEdit).isNotEmpty();
        Assertions.assertThat(studentEdit.getName()).isEqualTo(studentTest.getName());
        Assertions.assertThat(studentEdit.getAge()).isEqualTo(studentTest.getAge());
    }

    @Test
    void deleteStudent() throws Exception {
        Student studentTest = new Student(1L, "Test3", 3);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class);
        Student student = new ObjectMapper().readValue(response, Student.class);

        restTemplate.delete("http://localhost:" + port + "/student/" + student.getId());
        String responseEdit = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + student.getId(), String.class);

        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(responseEdit).isNull();
    }

    @Test
    void findByAgeBetween() throws Exception {
        Student studentTest1 = new Student(1L, "Test1", 1);
        Student studentTest2 = new Student(2L, "Test2", 5);
        Student studentTest3 = new Student(3L, "Test3", 15);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest1, String.class);
        String response2 = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest2, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest3, String.class);
        Student student2 = new ObjectMapper().readValue(response2, Student.class);

        String response = this.restTemplate.getForObject("http://localhost:" + port + "/student/by-age-between?min=2&max=10", String.class);

        Assertions.assertThat(response)
                .isEqualTo("[" + response2 + "]");
    }

    @Test
    void getFacultyByStudentId() throws Exception {
        Faculty facultyTest = new Faculty(1L, "name", "color");
        String facultyResponse = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class);
        Faculty faculty = new ObjectMapper().readValue(facultyResponse, Faculty.class);
//Faculty{id=1, name='name', color='color'}

        Student studentTest = new Student(1L, "Test1", 1);
        String studentResponse = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class);
        Student student = new ObjectMapper().readValue(studentResponse, Student.class);
        student.setFaculty(facultyTest);
//Student{id=1, name='Test1', age=1}
//1 setFaculty

        Faculty fromDB = restTemplate.getForObject("http://localhost:" + port + "/student/faculty-by-student-id/1", Faculty.class);

        System.out.println(fromDB);

        //todo:  помогите доделать тест

        //не могу разобрать почему возращает null
        //дальше тест не могу написать
        //просьба помочь с написанием этого теста

    }
}