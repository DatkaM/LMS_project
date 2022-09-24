package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.InstructorMapper;
import peaksoft.dto.request.CourseRequest;
import peaksoft.dto.request.InstructorRequest;
import peaksoft.dto.response.CourseResponse;
import peaksoft.dto.response.InstructorResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.InstructorResponseView;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.CompanyRepository;
import peaksoft.repositories.InstructorRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CompanyRepository companyRepository;
    private final InstructorMapper mapper;

    public InstructorResponse saveInstructor(InstructorRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new NotFoundException(String.format(
                                "Company with %d id not found", request.getCompanyId())
                        )
                );

        Instructor instructor = mapper.mapToEntity(request);
        company.addInstructor(instructor);
        instructor.setCompany(company);
        return mapper.mapToResponse(instructorRepository.save(instructor));
    }

    public InstructorResponse getInstructorResponseById(Long id) {
        return mapper.mapToResponse(instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Instructor with %d id not found", id)))
        );
    }

    public InstructorResponse updateInstructor(Long id, InstructorRequest request) {
        Instructor instructor = getInstructorById(id);
        Company company = getCompanyById(request.getCompanyId());
        if (!request.getCompanyId().equals(instructor.getCompany().getId())) {
            throw new BadRequestException(
                    "Instructor's company id and instructor request company id is different"
            );
        }

        instructor.setCompany(company);
        Instructor instructor1 = mapper.updateInstructor(instructor, request);
        return mapper.mapToResponse(instructorRepository.save(instructor1));
    }

    public SimpleResponse deleteInstructor(Long id) {
        boolean exists = instructorRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Instructor with %d id not found ", id)
            );
        }

        instructorRepository.deleteById(id);
        return new SimpleResponse("DELETE", String.format(
                "Instructor with %d id successfully deleted!", id)
        );
    }

    public InstructorResponseView searchInstructor(String text, int page, int size) {
        InstructorResponseView view = new InstructorResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        view.setInstructorResponses(instructorResponseList(instructorList(text, pageable)));
        return view;
    }

    private List<InstructorResponse> instructorResponseList(List<Instructor> instructors) {
        List<InstructorResponse> responses = new ArrayList<>();
        for (Instructor instructor : instructors) {
            responses.add(mapper.mapToResponse(instructor));
        }
        return responses;
    }

    private List<Instructor> instructorList(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return instructorRepository.getAll(name.toUpperCase(), pageable);
    }

    private Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Instructor with %d id not found", id)
                )
        );
    }

    private Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format(
                                "Company with %d id not found", id)
                        )
                );
    }

}
