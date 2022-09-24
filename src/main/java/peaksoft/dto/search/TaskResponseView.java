package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.TaskResponse;

import java.util.List;

@Getter
@Setter
public class TaskResponseView {

    private List<TaskResponse> taskResponses;
}
