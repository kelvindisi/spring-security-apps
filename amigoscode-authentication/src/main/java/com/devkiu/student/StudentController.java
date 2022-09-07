package com.devkiu.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private static final List<Student> students= List.of(
            new Student(1, "Kelvin Ndisi"),
            new Student(2, "Abigail Wavinya"),
            new Student(3, "Angela Mwende")
    );

    @PreAuthorize("hasAnyAuthority('student:read')")
    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Integer studentId) {
      return students.stream().filter(student -> Objects.equals(student.getStudentId(), studentId))
              .findFirst()
              .orElseThrow(()-> new IllegalStateException("no student found with that id."));
    }

}
