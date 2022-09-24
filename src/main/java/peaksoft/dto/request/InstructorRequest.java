package peaksoft.dto.request;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

@Getter
@Setter
public class InstructorRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String specialization;
    private Long companyId;
    private String email;
    private String password;


}
