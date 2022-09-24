package peaksoft.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import peaksoft.dto.request.LessonRequest;
import peaksoft.dto.response.LessonResponse;
import peaksoft.entity.Lesson;
import peaksoft.repositories.LessonRepository;

@Component
public class LessonMapper {


    public Lesson mapToEntity(LessonRequest request) {
        return Lesson.builder()
                .lessonName(request.getLessonName())
                .build();
    }

    public LessonResponse mapToResponse(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .lessonName(lesson.getLessonName())
                .courseId(lesson.getCourse().getId())
                .build();
    }

    public Lesson update(Lesson lesson, LessonRequest request) {
        lesson.setLessonName(request.getLessonName());
        return lesson;
    }

}