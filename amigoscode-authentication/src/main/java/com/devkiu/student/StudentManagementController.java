package com.devkiu.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@PreAuthorize("hasAnyRole('ADMIN', 'ADMIN_TRAINEE')")
@RestController
@RequestMapping("/api/v1/management/students")
public class StudentManagementController {
    private static final List<Student> students = List.of(
            new Student(1, "Kelvin Ndisi"),
            new Student(2, "Abigail Wavinya"),
            new Student(3, "Angela Mwende")
    );

    @GetMapping
    public List<Student> allStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Integer studentId) {
        return students.stream().filter(student -> Objects.equals(student.getStudentId(), studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no student found with that id."));
    }

    @PreAuthorize("hasAuthority('student:write')")
    @PostMapping
    public void registerStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @PreAuthorize("hasAuthority('student:write')")
    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Integer studentId) {
        System.out.printf("Student with %d deleted", studentId);
    }

    @PreAuthorize("hasAuthority('student:write')")
    @PutMapping("/{studentId}")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
        System.out.printf("Updated student with id: %d with " + student, studentId);
    }
}
