package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertNotNull(studentController);
    }

    @Test
    public void testGetStudentInfo() throws Exception {
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class),
                "{\"id\":1,\"name\":\"string\",\"age\":0,\"faculty\":{\"id\":1,\"name\":\"1A\",\"color\":\"111\",\"studentList\":[]}}");
    }
    @Test
    public void testGetStudentInfoAge() throws Exception {
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/student?age=2", String.class),
                "[{\"id\":2,\"name\":\"BBB\",\"age\":2,\"faculty\":{\"id\":1,\"name\":\"1A\",\"color\":\"111\",\"studentList\":[]}}]");
    }
    @Test
    public void testGetStudentInfoBetweenAge() throws Exception {
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/student/age-between?min=11&max=12", String.class),
                "[{\"id\":2,\"name\":\"BBB\",\"age\":12,\"faculty\":{\"id\":1,\"name\":\"1A\",\"color\":\"111\",\"studentList\":[]}}]");
    }
    @Test
    public void testGetStudentInfoFacultyByStudentId() throws Exception {
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty-by-student-id?id=1", String.class),
                "{\"id\":1,\"name\":\"1A\",\"color\":\"111\",\"studentList\":[]}");
    }
    @Test
    public void testPostStudent() throws Exception{
        Student studentTest = new Student();
        studentTest.setName("Фродо");
        studentTest.setAge(20);
        Assertions.assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class));

    }
    @Test
    public void testDeleteStudent() throws Exception{
        ResponseEntity<Void> resp = restTemplate.exchange("http://localhost:" + port + "/student/123", HttpMethod.DELETE, HttpEntity.EMPTY,Void.class);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,resp.getStatusCode());
    }
    @Test
    public void testPutStudent() throws Exception{
        Student studentTest = new Student(11L,"Гендальф",200);
        restTemplate.put("http://localhost:" + port + "/student/11", studentTest);

        Assertions.assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class));

    }
    @Test
    public void contextLoadsFaculty() throws Exception {
        Assertions.assertNotNull(facultyController);
    }
    @Test
    public void testGetFacultyInfo() throws Exception {
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class),
                "{\"id\":1,\"name\":\"1A\",\"color\":\"111\",\"studentList\":[]}");
    }
    @Test
    public void testGetFacultyInfoColorOrName() throws Exception {
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/get-color-or-name?str=222", String.class),
                "[{\"id\":2,\"name\":\"2B\",\"color\":\"222\",\"studentList\":[]}]");
    }
    @Test
    public void testGetStudentInfoByFacultyIdId() throws Exception {
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/get-student-by-faculty-id?id=1", String.class),
                "[{\"id\":2,\"name\":\"BBB\",\"age\":12,\"faculty\":{\"id\":1,\"name\":\"1A\",\"color\":\"111\",\"studentList\":[]}},{\"id\":1,\"name\":\"string\",\"age\":5,\"faculty\":{\"id\":1,\"name\":\"1A\",\"color\":\"111\",\"studentList\":[]}}]");
    }
    @Test
    public void testPostFaculty() throws Exception{
        Faculty facultyTest = new Faculty();
        facultyTest.setName("Орки");
        facultyTest.setColor("Черный");
        Assertions.assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class));
    }
    @Test
    public void testDeleteFaculty() throws Exception{
        ResponseEntity<Void> resp = restTemplate.exchange("http://localhost:" + port + "/faculty/123", HttpMethod.DELETE, HttpEntity.EMPTY,Void.class);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,resp.getStatusCode());
    }
    @Test
    public void testPutFaculty() throws Exception{
        Faculty facultyTest = new Faculty(4L,"Хоббиты","Белый");
        restTemplate.put("http://localhost:" + port + "/faculty/4", facultyTest);

        Assertions.assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class));
    }
}




