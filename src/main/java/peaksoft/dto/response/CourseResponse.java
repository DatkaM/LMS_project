package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CourseResponse {

    private Long id;
    private String courseName;
    private LocalDate dateOfStart;
    private String duration;
    private String image;
    private String description;
    private Long companyId;


}
