package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.LessonResponse;

import java.util.List;

@Getter
@Setter
public class LessonResponseView {

    private List<LessonResponse> lessonResponses;
}
