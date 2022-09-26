package peaksoft.dto.response;

import lombok.*;
import peaksoft.enums.Role;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class StudentResponse {

    private Long id;
    private String fullName;
    private String email;
    private CourseResponse courseResponse;
    private Role role;


}
