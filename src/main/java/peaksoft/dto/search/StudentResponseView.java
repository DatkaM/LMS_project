package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.StudentResponse;

import java.util.List;

@Getter
@Setter
public class StudentResponseView {

    private List<StudentResponse> studentResponses;
}
