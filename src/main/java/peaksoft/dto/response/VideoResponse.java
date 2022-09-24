package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class VideoResponse {

    private Long id;
    private String videoName;
    private String link;
    private Long lessonId;

}
