package ru.hogwarts.school;


import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SchoolApplicationWithMockTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private StudentController studentController;
    @InjectMocks
    private FacultyController facultyController;

    Student STUDENT_1 = new Student(1L, "Леголас", 450);
    Student STUDENT_2 = new Student(2L, "Арагорн", 35);
    Student STUDENT_3 = new Student(3L, "Гимли", 62);
    Student STUDENT_4 = new Student(4L, "Дурин", 150);
    Faculty FACULTY_1 = new Faculty(1L, "Эльфы", "Белый");
    Faculty FACULTY_2 = new Faculty(2L, "Люди", "Желтый");
    Faculty FACULTY_3 = new Faculty(3L, "Гномы", "Коричневый");


    @Test
    public void saveAndGetStudentTest() throws Exception {
        final String name = "Дамблдор";
        final int age = 150;
        final long id = 1;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    public void studentGetByAgeTest() throws Exception {
        when(studentRepository.getAllByAge(35)).thenReturn(List.of(STUDENT_2));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?age=35")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("Арагорн"))
                .andExpect(jsonPath("$[0].age").value(35));


    }

    @Test
    public void getStudentByAgeBetweenTest() throws Exception {
        when(studentRepository.findAllByAgeBetween(60, 500)).thenReturn(List.of(STUDENT_1, STUDENT_3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age-between?min=60&max=500")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("Гимли"))
                .andExpect(jsonPath("$[0].age").value(450));

    }
    @Test
    public void getFacultyByStudentId() throws Exception {
        STUDENT_1.setFaculty(FACULTY_1);
        STUDENT_2.setFaculty(FACULTY_2);
        STUDENT_3.setFaculty(FACULTY_3);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(STUDENT_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty-by-student-id?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Эльфы"))
                .andExpect(jsonPath("$.color").value("Белый"));
    }
    @Test
    public void editStudentTest() throws Exception {
        final String name = "Дамблдор";
        final int age = 150;
        final long id = 1;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
    @Test
    public void deleteStudentTest() throws Exception {
        final String name = "Дамблдор";
        final int age = 150;
        final long id = 1;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void saveAndGetFacultyTest() throws Exception {
        final String name = "Орки";
        final String color = "Черный";
        final long id = 1;
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color );
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
    @Test
    public void facultyGetByColorTest() throws Exception {
        when(facultyRepository.getAllByColor("Белый")).thenReturn(List.of(FACULTY_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=Белый")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Эльфы"))
                .andExpect(jsonPath("$[0].color").value("Белый"));
    }
    @Test
    public void facultyGetByColorOrNameTest() throws Exception {
        when(facultyRepository.getAllByNameIgnoreCase("Люди")).thenReturn(List.of(FACULTY_2));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get-color-or-name?str=Люди")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("Люди"))
                .andExpect(jsonPath("$[0].color").value("Желтый"));
    }
    @Test
    public void getStudentByFacultyId() throws Exception {
        List<Student> STUDENTLIST = new ArrayList<>(List.of(STUDENT_3,STUDENT_4));
        when(studentRepository.findByFacultyId(3L)).thenReturn(STUDENTLIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get-student-by-faculty-id?id=3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3L))
                .andExpect(jsonPath("$[1].name").value("Дурин"))
                .andExpect(jsonPath("$[0].age").value(62));
    }
    @Test
    public void editFacultyTest() throws Exception {
        final String name = "Эльфы";
        final String color = "Белый";
        final long id = 1;
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
    @Test
    public void deleteFacultyTest() throws Exception {
        final String name = "Эльфы";
        final String color = "Белый" ;
        final long id = 1;
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
