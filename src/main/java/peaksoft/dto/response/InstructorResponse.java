package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

import java.util.List;

@Getter
@Setter
public class InstructorResponse {

    private Long id;
    private String fullName;
    private String email;
    private List<CourseResponse> courseResponses;
    private Role role;

}
