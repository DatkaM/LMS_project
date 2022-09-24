package peaksoft.dto.mapper;

import org.springframework.stereotype.Component;
import peaksoft.dto.request.VideoRequest;
import peaksoft.dto.response.VideoResponse;
import peaksoft.entity.Video;

@Component

public class VideoMapper {


    public Video mapToEntity(VideoRequest request) {
        return Video.builder()
                .videoName(request.getVideoName())
                .link(request.getLink())
                .build();
    }

    public VideoResponse mapToResponse(Video video) {
        return VideoResponse.builder()
                .id(video.getId())
                .videoName(video.getVideoName())
                .link(video.getLink())
                .lessonId(video.getLesson().getId())
                .build();
    }

    public Video updateVideo(Video video, VideoRequest request) {
        video.setVideoName(request.getVideoName());
        video.setLink(request.getLink());
        return video;
    }
}
