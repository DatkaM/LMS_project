package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.InstructorRequest;
import peaksoft.dto.response.InstructorResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.InstructorResponseView;
import peaksoft.service.InstructorService;

@RestController
@RequestMapping("api/instructor")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping()
    public InstructorResponse saveInstructor(@RequestBody InstructorRequest request) {
        return instructorService.saveInstructor(request);
    }

    @GetMapping("{id}")
    public InstructorResponse getInstructorResponseById(@PathVariable Long id) {
        return instructorService.getInstructorResponseById(id);
    }

    @PutMapping("{id}")
    public InstructorResponse updateInstructor(@PathVariable Long id,
                                               @RequestBody InstructorRequest request) {
        return instructorService.updateInstructor(id, request);
    }

    @DeleteMapping("{id}")
    public SimpleResponse deleteInstructor(@PathVariable Long id) {
        return instructorService.deleteInstructor(id);
    }

    @GetMapping
    public InstructorResponseView search(@RequestParam(name = "text",required = false)String text,
                                         @RequestParam int page,
                                         @RequestParam int size){
        return instructorService.searchInstructor(text,page,size);
    }
}

/**
 * "firstName":"Aijamal",
 * "lastName":"Asangazieva",
 * "phoneNumber":"986798",
 * "email":"aijamal@gmail.com",
 * "specialization":"java mentor",
 * "companyId":2
 */
