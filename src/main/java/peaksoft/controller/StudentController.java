package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AssignStudentToCourseRequest;
import peaksoft.dto.request.StudentRequest;
import peaksoft.dto.response.AssignStudentToCourseResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StudentResponse;
import peaksoft.dto.search.StudentResponseView;
import peaksoft.service.StudentService;

@RestController
@RequestMapping("api/student")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
public class StudentController {

    private final StudentService studentService;


    @PostMapping
    public StudentResponse saveStudent(@RequestBody StudentRequest request){
        return studentService.saveStudent(request);
    }

    @GetMapping("{id}")
    public StudentResponse getStudentById(@PathVariable Long id){
        return studentService.getStudentResponseById(id);
    }

    @PutMapping("{id}")
    public StudentResponse updateStudent(@PathVariable Long id,
                                         @RequestBody StudentRequest request){
        return studentService.updateStudent(id,request);
    }

    @DeleteMapping("{id}")
    public SimpleResponse deleteStudent(@PathVariable Long id){
        return studentService.deleteStudent(id);
    }

    @GetMapping
    public StudentResponseView search(@RequestParam(name = "text",required = false)String text,
                                      @RequestParam int page,
                                      @RequestParam int size){
        return studentService.search(text, page, size);
    }

    @PostMapping("/assignStudent")
    public AssignStudentToCourseResponse assignStudentToCourse(@RequestBody AssignStudentToCourseRequest request) {
        return studentService.assignStudentToCourse(request);
    }

}

