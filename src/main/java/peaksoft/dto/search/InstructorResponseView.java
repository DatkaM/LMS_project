package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.InstructorResponse;

import java.util.List;

@Getter@Setter
public class InstructorResponseView {

    private List<InstructorResponse> instructorResponses;
}
