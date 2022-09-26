package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.StudentMapper;
import peaksoft.dto.request.AssignStudentToCourseRequest;
import peaksoft.dto.request.StudentRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StudentResponse;
import peaksoft.dto.search.StudentResponseView;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Student;

import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.CompanyRepository;
import peaksoft.repositories.CourseRepository;
import peaksoft.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper mapper;

    public StudentResponse saveStudent(StudentRequest request) {
        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format(
                        "Student with %d id not found", request.getCompanyId())
                )
        );

        Student student = mapper.mapToEntity(request);
        company.addStudent(student);
        student.setCompany(company);
        return mapper.mapToResponse(studentRepository.save(student));
    }

    public StudentResponse getStudentResponseById(Long id) {
        return mapper.mapToResponse(studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(
                                "Student with %d id not found", id))
                )
        );
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = getById(id);
        Company company = getCompanyById(request.getCompanyId());
        if (!student.getCompany().getId().equals(request.getCompanyId())) {
            throw new BadRequestException(
                    "Student's company id and student request's company id are different"
            );
        }

        company.addStudent(student);
        student.setCompany(company);
        Student student1 = mapper.updateStudent(student, request);
        return mapper.mapToResponse(studentRepository.save(student1));
    }

    public SimpleResponse deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Student with %d id not found", id
            )
            );
        }

        studentRepository.deleteById(id);
        return new SimpleResponse("DELETE",
                String.format(
                        "Student with %d id successfully deleted", id)
        );
    }

    public StudentResponseView search(String text, int page, int size) {
        StudentResponseView view = new StudentResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        view.setStudentResponses(studentResponses(students(text, pageable)));
        return view;
    }

    public List<StudentResponse> studentResponses(List<Student> students) {
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(mapper.mapToResponse(student));
        }
        return studentResponses;
    }

    public List<Student> students(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return studentRepository.getAllStudent(name.toUpperCase(), pageable);
    }


    public StudentResponse assignStudentToCourse(AssignStudentToCourseRequest request) {
        Student student = getById(request.getStudentId());
        Course course = getCourseById(request.getCourseId());
        if (!student.getCompany().getId().equals(course.getCompany().getId())) {
            throw new BadRequestException(
                    "Student and course are in DIFFERENT company!"
            );
        }

        course.addStudent(student);
        student.setCourse(course);
        courseRepository.save(course);
        studentRepository.save(student);
        return mapper.mapToResponse(student);
    }

    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(
                        "Student with %d id not found", id)
                )
        );
    }

    private Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(
                        "Course with %d id not found", id)
                )
        );
    }

    private Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format("Company with %d id not found",id))
        );
    }
}
