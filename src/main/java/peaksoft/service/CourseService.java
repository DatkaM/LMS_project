package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.CourseMapper;
import peaksoft.dto.mapper.InstructorMapper;
import peaksoft.dto.request.AssignInstructorToCourseRequest;
import peaksoft.dto.request.CourseRequest;
import peaksoft.dto.response.CourseResponse;
import peaksoft.dto.response.InstructorResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.CourseResponseView;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.CompanyRepository;
import peaksoft.repositories.CourseRepository;
import peaksoft.repositories.InstructorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final InstructorRepository instructorRepository;
    private final CourseMapper mapper;
    private final InstructorMapper instructorMapper;

    public CourseResponse saveCourse(CourseRequest request) {
        Course course = mapper.mapToEntity(request);
        Company company = getCompanyById(request.getCompanyId());
        company.addCourse(course);
        course.setCompany(company);
        return mapper.mapToResponse(courseRepository.save(course));
    }

    public CourseResponse getCourseResponseById(Long id) {
        return mapper.mapToResponse(courseRepository.findById(id).orElseThrow(() ->
                        new NotFoundException(String.format("Course with %d id not found", id))
                )
        );
    }

    public SimpleResponse deleteCourseById(Long id) {
        boolean exists = courseRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Course with %d id not found", id
            ));
        }
        courseRepository.deleteById(id);
        return new SimpleResponse("DELETED",
                "Course with " + id + " id successfully deleted"
        );
    }

    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = getCourseById(id);
        Company company = getCompanyById(request.getCompanyId());
        if (!request.getCompanyId().equals(course.getCompany().getId())) {
             throw new BadRequestException(
                     "Course's company id and course request company id is different");
        }
        course.setCompany(company);
        Course course1 = mapper.update(course, request);
        return mapper.mapToResponse(courseRepository.save(course1));
    }

    public CourseResponseView search(String text, int page, int size,String email) {
        CourseResponseView view = new CourseResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        view.setResponses(courseResponses(getCourses(text, pageable,email)));
        return view;
    }

    private List<CourseResponse> courseResponses(List<Course> courses) {
        List<CourseResponse> responses = new ArrayList<>();
        for (Course course : courses) {
            responses.add(mapper.mapToResponse(course));
        }
        return responses;
    }

    private List<Course> getCourses(String text, Pageable pageable,String email) {
        String s = text == null ? "" : text;
        return courseRepository.getAll(s.toUpperCase(), pageable,email);
    }


    public InstructorResponse assignInstructorToCourse(AssignInstructorToCourseRequest request) {
        Instructor instructor = getInstructorById(request.getInstructorId());
        Course course = getCourseById(request.getCourseId());
        if (!instructor.getCompany().getId().equals(course.getCompany().getId())) {
            throw new BadRequestException(
                    "Instructor and course are in DIFFERENT companies!"
            );
        }
        instructor.addCourse(course);
        course.addInstructor(instructor);
        instructorRepository.save(instructor);
        courseRepository.save(course);
        return instructorMapper.mapToResponse(instructor);
    }

    private Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Course with %d id not found", id)
                )
        );
    }

    private Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Instructor with %d id not found", id)
                )
        );
    }

    private Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Company with %d id not found", id)
                )
        );
    }
}
