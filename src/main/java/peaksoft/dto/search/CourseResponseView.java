package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.CourseResponse;

import java.util.List;

@Getter
@Setter
public class CourseResponseView {

    private List<CourseResponse> responses;
}
