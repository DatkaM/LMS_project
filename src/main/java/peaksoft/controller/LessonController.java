package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.LessonRequest;
import peaksoft.dto.response.LessonResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.LessonResponseView;
import peaksoft.service.LessonService;


@RestController
@RequestMapping("api/lesson")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public LessonResponse saveLesson(@RequestBody LessonRequest request){
        return lessonService.saveLesson(request);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public LessonResponse getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PutMapping("{id}")
    public LessonResponse updateLesson(@PathVariable Long id,
                                       @RequestBody LessonRequest request) {
        return lessonService.updateLesson(id,request);
    }

    @DeleteMapping("{id}")
    public SimpleResponse deleteLesson(@PathVariable Long id){
        return lessonService.deleteLesson(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    public LessonResponseView search(@RequestParam(name = "text",required = false) String text,
                                     @RequestParam int page,
                                     @RequestParam int size) {
        return lessonService.searchLesson(text, page, size);
    }


}
