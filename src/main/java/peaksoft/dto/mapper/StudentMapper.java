package peaksoft.dto.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import peaksoft.dto.request.StudentRequest;
import peaksoft.dto.response.StudentResponse;
import peaksoft.entity.Student;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.BadRequestException;
import peaksoft.repositories.StudentRepository;
import peaksoft.security_mvc_file.jwt.JwtTokenUtil;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class StudentMapper {
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final JwtTokenUtil tokenUtil;

    public Student mapToEntity(StudentRequest request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setStudyFormat(request.getStudyFormat());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        student.setUser(user);
        return student;
    }

    public StudentResponse mapToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFullName(student.getFirstName() + " " + student.getLastName());
        response.setEmail(student.getUser().getEmail());
        response.setRole(student.getUser().getRole());
        return response;
    }

    public Student updateStudent(Student student, StudentRequest request) {
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setStudyFormat(request.getStudyFormat());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(user.getRole());
        student.setUser(user);
        return student;
    }
}
