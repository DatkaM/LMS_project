package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.VideoMapper;
import peaksoft.dto.request.VideoRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.VideoResponse;
import peaksoft.dto.search.VideoResponseView;
import peaksoft.entity.Lesson;
import peaksoft.entity.Video;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.LessonRepository;
import peaksoft.repositories.VideoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;
    private final VideoMapper mapper;

    public VideoResponse saveVideo(VideoRequest request) {
        Lesson lesson = getLessonById(request.getLessonId());
        Video video = mapper.mapToEntity(request);
        video.setLesson(lesson);
        lesson.setVideo(video);
        return mapper.mapToResponse(videoRepository.save(video));
    }

    public VideoResponse getVideoResponseById(Long id) {
        return mapper.mapToResponse(getVideoById(id));
    }

    public SimpleResponse deleteVideo(Long id) {
        boolean exists = videoRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Video with %d id not found", id));
        }

        return new SimpleResponse(
                "DELETE", String.format(
                        "Video with %d id successfully deleted", id));
    }

    public VideoResponse updateVideo(Long id, VideoRequest request) {
        Video video = getVideoById(id);
        Lesson lesson = getLessonById(request.getLessonId());
        if (!video.getLesson().getId().equals(request.getLessonId())) {
            throw new BadRequestException(
                    "Video's lesson id and video request's id are different"
            );
        }

        lesson.setVideo(video);
        video.setLesson(lesson);
        Video video1 = mapper.updateVideo(video, request);
        return mapper.mapToResponse(videoRepository.save(video1));
    }


    public VideoResponseView search(String text, int page, int size) {
        VideoResponseView view = new VideoResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        view.setVideoResponses(getVideoResponses(getVideos(text, pageable)));
        return view;
    }

    private List<VideoResponse> getVideoResponses(List<Video> videos) {
        List<VideoResponse> responses = new ArrayList<>();
        for (Video video : videos) {
            responses.add(mapper.mapToResponse(video));
        }
        return responses;
    }

    private List<Video> getVideos(String text, Pageable pageable) {
        String t = text == null ? "" : text;
        return videoRepository.getAllVideo(t.toUpperCase(), pageable);
    }

    private Video getVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Video with %d id not found", id))
        );
    }

    private Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Lesson with %d id not found", id)));
    }

}