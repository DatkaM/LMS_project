package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AssignInstructorToCourseRequest;
import peaksoft.dto.request.CourseRequest;
import peaksoft.dto.response.AssignInstructorToCourseResponse;
import peaksoft.dto.response.CourseResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.CourseResponseView;
import peaksoft.service.CourseService;
import peaksoft.service.InstructorService;


@RestController
@RequestMapping("api/course")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public CourseResponse save(@RequestBody CourseRequest request) {
        return courseService.saveCourse(request);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public CourseResponse getCourseResponseById(@PathVariable Long id) {
        return courseService.getCourseResponseById(id);
    }

    @DeleteMapping("{id}")
    public SimpleResponse deleteCourseById(@PathVariable Long id) {
        return courseService.deleteCourseById(id);
    }

    @PutMapping("{id}")
    public CourseResponse update(@PathVariable Long id, @RequestBody CourseRequest request) {
        return courseService.updateCourse(id, request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    public CourseResponseView search(@RequestParam(name = "text", required = false) String text,
                                     @RequestParam int page,
                                     @RequestParam int size) {
        return courseService.search(text, page, size);
    }

    @PostMapping("/assignInstructor")
    public AssignInstructorToCourseResponse assignInstructorToCourse(@RequestBody AssignInstructorToCourseRequest request) {
        return courseService.assignInstructorToCourse(request);
    }


}
/**
 * "courseName":"Js-6",
 * "dateOfStart":"2022-04-04",
 * "duration":"9-month",
 * "image":"image",
 * "description":"des",
 * "companyId":3
 */