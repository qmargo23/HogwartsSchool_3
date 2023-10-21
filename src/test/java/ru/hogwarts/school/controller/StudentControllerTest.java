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
import ru.hogwarts.school.service.StudentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    StudentService studentService;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentService).isNotNull();
    }

    @Test
    public void addStudent() throws Exception {
        ResponseEntity<Student> newStudentResponse = restTemplate.postForEntity("http://localhost:" + port + "/student", new Student(0L, "Test_Student", 12), Student.class);
        Student newStudent = newStudentResponse.getBody();

        //готовим запрос   и готовим student через ObjectMapper()
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/student", newStudent, String.class);
        Student student = new ObjectMapper().readValue(response, Student.class);

        //проверка
        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo(newStudent.getName());
        Assertions.assertThat(student.getAge()).isEqualTo(newStudent.getAge());
    }

    @Test
    public void getStudentInfo() throws Exception {
        ResponseEntity<Student> newStudentResponse = restTemplate.postForEntity("http://localhost:" + port + "/student", new Student(0L, "Test_Student", 12), Student.class);
        Student newStudent = newStudentResponse.getBody();

        ResponseEntity<Student> getStudentInfoResponse = restTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);
        Student getStudentInfo = getStudentInfoResponse.getBody();

        Assertions
                .assertThat(getStudentInfo).isEqualTo(newStudent);
    }

    //
    //
    //
    //
    //
    //
    @Test
    public void editStudent() throws Exception {
        ResponseEntity<Faculty> newFacultyResponse = restTemplate.postForEntity("http://localhost:" + port + "/faculty", new Faculty(0L, "New faculty", "red"), Faculty.class);
        Faculty newFaculty = newFacultyResponse.getBody();
        ResponseEntity<Student> newStudentResponse = restTemplate.postForEntity("http://localhost:" + port + "/student", new Student(0L, "Test1", 12, newFaculty), Student.class);
        Student newStudent = newStudentResponse.getBody();
        newStudent.setName("TEST2");
        newStudent.setAge(23);

        restTemplate.put("http://localhost:" + port + "/student", newStudent);
//        ResponseEntity<Student> getStudentInfoResponse = restTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);
//        Student getStudentInfo = getStudentInfoResponse.getBody();

        String response = this.restTemplate.getForObject("http://localhost:" + port + "/student/" + newStudent.getId(), String.class);
        Student student = new ObjectMapper().readValue(response, Student.class);

        System.out.println(student);

        Assertions
                .assertThat(student.getName()).isEqualTo(newStudent.getName());
        Assertions
                .assertThat(student.getAge()).isEqualTo(newStudent.getAge());
    }

    @Test
    public void deleteStudent() throws Exception {
        ResponseEntity<Student> newStudentResponse = restTemplate.postForEntity("http://localhost:" + port + "/student", new Student(0L, "name", 12), Student.class);
        Student newStudent = newStudentResponse.getBody();

        restTemplate.delete("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);
        ResponseEntity<Student> studentEntity = restTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);
        Student student = studentEntity.getBody();

        Assertions
                .assertThat(newStudent).isNotNull();
        Assertions
                .assertThat(student).isNull();
    }

    @Test
    public void findByAgeBetween() throws Exception {
        Student studentTest1 = new Student(1L, "Test1", 1);
        Student studentTest2 = new Student(2L, "Test2", 5);
        Student studentTest3 = new Student(3L, "Test3", 15);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest1, String.class);
        String response2 = this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest2, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest3, String.class);

        String response = this.restTemplate.getForObject("http://localhost:" + port + "/student/by-age-between?min=2&max=10", String.class);

        Assertions.assertThat(response)
                .isEqualTo("[" + response2 + "]");
    }

    @Test
    public void getFacultyByStudentId() throws Exception {
        ResponseEntity<Faculty> newFacultyResponse = restTemplate.postForEntity("http://localhost:" + port + "/faculty", new Faculty(0L, "New faculty", "red"), Faculty.class);
        Faculty newFaculty = newFacultyResponse.getBody();
        ResponseEntity<Student> newStudentResponse = restTemplate.postForEntity("http://localhost:" + port + "/student", new Student(0L, "Test_Student", 12, newFaculty), Student.class);
        Student newStudent = newStudentResponse.getBody();

        ResponseEntity<Faculty> getFacultyByStudentResponse = restTemplate.getForEntity("http://localhost:" + port + "/student/faculty-by-student-id?id=" + newStudent.getId(), Faculty.class);
        Faculty getFacultyByStudent = getFacultyByStudentResponse.getBody();

        Assertions.assertThat(getFacultyByStudent).isEqualTo(newStudent.getFaculty());
    }
}