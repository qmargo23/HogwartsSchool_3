package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long countF = 0;

    public Faculty addFaculty(Faculty faculty) {//create-POST
        faculty.setId(++countF);
        faculties.put(countF, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {//read-GET
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {//update-PUT
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {//delete-DELETE
        return faculties.remove(id);
    }

    public Collection<Faculty> findByColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        return result;
    }
}
