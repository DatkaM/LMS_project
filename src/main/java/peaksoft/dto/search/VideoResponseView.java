package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.VideoResponse;

import java.util.List;

@Getter
@Setter
public class VideoResponseView {

    private List<VideoResponse> videoResponses;
}
