package peaksoft.dto.request;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.enums.StudyFormat;

@Getter
@Setter
public class StudentRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private StudyFormat studyFormat;
    private Long companyId;
    private String email;
    private String password;

}
