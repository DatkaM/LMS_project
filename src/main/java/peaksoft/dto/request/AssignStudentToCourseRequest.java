package peaksoft.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignStudentToCourseRequest {
    private Long studentId;
    private Long courseId;

}
