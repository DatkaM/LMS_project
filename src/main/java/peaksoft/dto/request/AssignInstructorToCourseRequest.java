package peaksoft.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AssignInstructorToCourseRequest {

    private Long instructorId;
    private Long courseId;
}
