package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.LessonMapper;
import peaksoft.dto.request.LessonRequest;
import peaksoft.dto.response.LessonResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.LessonResponseView;
import peaksoft.entity.Course;
import peaksoft.entity.Lesson;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.CourseRepository;
import peaksoft.repositories.LessonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper mapper;


    public LessonResponse saveLesson(LessonRequest request) {
        Lesson lesson = mapper.mapToEntity(request);
        Course course = getCourseById(request.getCourseId());
        course.addLesson(lesson);
        lesson.setCourse(course);
        return mapper.mapToResponse(lessonRepository.save(lesson));
    }

    public LessonResponse getLessonById(Long id) {
        return mapper.mapToResponse(lessonRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Lesson with %d id not found", id))
        ));
    }

    public LessonResponse updateLesson(Long id, LessonRequest request) {
        Lesson lesson = getLesById(id);
        Course course = getCourseById(request.getCourseId());
        if (!request.getCourseId().equals(lesson.getCourse().getId())) {
            throw new BadRequestException(
                    "Lesson's course id and lesson request's course id are different"
            );
        }
        course.addLesson(lesson);
        lesson.setCourse(course);
        Lesson lesson1 = mapper.update(lesson,request);
        return mapper.mapToResponse(lessonRepository.save(lesson1));
    }

    public SimpleResponse deleteLesson(Long id) {
        boolean exists = lessonRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Lesson with %d id not found", id));
        }
        lessonRepository.deleteById(id);
        return new SimpleResponse("DELETE", String.format(
                "Lesson with %d id successfully deleted", id));
    }

    public LessonResponseView searchLesson(String text, int page, int size) {
        LessonResponseView view = new LessonResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        view.setLessonResponses(getLessonResponses(getLessons(text, pageable)));
        return view;
    }

    private List<LessonResponse> getLessonResponses(List<Lesson> lessonList) {
        List<LessonResponse> lessonResponses = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            lessonResponses.add(mapper.mapToResponse(lesson));
        }
        return lessonResponses;
    }

    private List<Lesson> getLessons(String text, Pageable pageable) {
        String t = text == null ? "" : text;
        return lessonRepository.getAllLesson(t.toUpperCase(), pageable);
    }


    private Lesson getLesById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with %d id not found", id)
                )
        );
    }

    private Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Course with %d id not found", id)
                )
        );
    }


}
