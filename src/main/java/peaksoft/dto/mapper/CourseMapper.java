package peaksoft.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import peaksoft.dto.request.CourseRequest;
import peaksoft.dto.response.CourseResponse;
import peaksoft.entity.Course;

@Component
@RequiredArgsConstructor
public class CourseMapper {


    public Course mapToEntity(CourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setDateOfStart(request.getDateOfStart());
        course.setDuration(request.getDuration());
        course.setImage(request.getImage());
        course.setDescription(request.getDescription());
        return course;
    }

    public CourseResponse mapToResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setCourseName(course.getCourseName());
        response.setDateOfStart(course.getDateOfStart());
        response.setDuration(course.getDuration());
        response.setImage(course.getImage());
        response.setDescription(course.getDescription());
        response.setCompanyId(course.getCompany().getId());
        return response;
    }

    public Course update(Course course, CourseRequest request) {
        course.setCourseName(request.getCourseName());
        course.setDateOfStart(request.getDateOfStart());
        course.setDuration(request.getDuration());
        course.setImage(request.getImage());
        course.setDescription(request.getDescription());
        return course;
    }

}
