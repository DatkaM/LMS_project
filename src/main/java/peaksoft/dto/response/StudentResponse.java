package peaksoft.dto.response;

import lombok.*;
import peaksoft.enums.Role;


@Getter
@Setter
@NoArgsConstructor
public class StudentResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;


}
