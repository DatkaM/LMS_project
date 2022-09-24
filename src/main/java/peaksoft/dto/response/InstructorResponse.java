package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

@Getter
@Setter
public class InstructorResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;

}
