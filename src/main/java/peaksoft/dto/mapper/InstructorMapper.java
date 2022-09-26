package peaksoft.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import peaksoft.dto.request.InstructorRequest;
import peaksoft.dto.response.InstructorResponse;
import peaksoft.entity.Instructor;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.repositories.InstructorRepository;

@Component
@RequiredArgsConstructor
public class InstructorMapper {
    private final PasswordEncoder passwordEncoder;
    private final CourseMapper mapper;
    private final InstructorRepository instructorRepository;

    public Instructor mapToEntity(InstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setPhoneNumber(request.getPhoneNumber());
        instructor.setSpecialization(request.getSpecialization());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.INSTRUCTOR);
        instructor.setUser(user);
        return instructor;
    }

    public InstructorResponse mapToResponse(Instructor instructor) {
        InstructorResponse response = new InstructorResponse();
        response.setId(instructor.getId());
        response.setFullName(instructor.getFirstName() + " " + instructor.getLastName());
        response.setEmail(instructor.getUser().getEmail());
        response.setCourseResponses(mapper.mapToResponseList(instructor.getCourses()));
        response.setRole(instructor.getUser().getRole());
        return response;
    }

    public Instructor updateInstructor(Instructor instructor, InstructorRequest request) {
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setPhoneNumber(request.getPhoneNumber());
        instructor.setSpecialization(request.getSpecialization());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(instructor.getUser().getRole());
        instructor.setUser(user);
        return instructor;
    }

//    public AssignInstructorToCourseResponse assignInstructorToCourse(AssignInstructorToCourseRequest request) {
//        AssignInstructorToCourseResponse response = new AssignInstructorToCourseResponse();
//        response.setId(request.getInstructorId());
//        Instructor instructor = instructorRepository.findById(request.getInstructorId()).get();
//        response.setFullName(instructor.getFirstName() + " " + instructor.getLastName());
//        response.setInstructorCourses(mapper.mapToResponseList(instructor.getCourses()));
//        return response;
//    }

}
