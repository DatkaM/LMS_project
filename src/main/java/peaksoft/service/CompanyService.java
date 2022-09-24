package peaksoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.dto.mapper.CompanyMapper;
import peaksoft.dto.request.CompanyRequest;
import peaksoft.dto.response.CompanyResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.CompanyResponseView;
import peaksoft.entity.Company;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositories.CompanyRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;

    public CompanyResponse saveCompany(CompanyRequest request) {
        Company company = mapper.mapToEntity(request);
        return mapper.mapToResponse(companyRepository.save(company));
    }

    public CompanyResponse getCompanyById(Long id) {
        return mapper.mapToResponse(companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Company with %d id not found", id)))
        );
    }

    public SimpleResponse deleteCompanyById(Long id) {
        boolean exists = companyRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(String.format(
                    "Company with %d id not found!", id)
            );
        }
        companyRepository.deleteById(id);
        return new SimpleResponse(
                "DELETED",
                String.format("Company with %d id successfully deleted",id));
    }

    public CompanyResponse updateCompany(Long id, CompanyRequest request) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Company with %d id not found", id)
                )
        );
        return mapper.mapToResponse(companyRepository.save(company.builder()
                        .id(company.getId())
                        .companyName(request.getCompanyName())
                        .locatedCountry(request.getLocatedCountry())
                .build()));
    }

    public CompanyResponseView searchByCompanyName(String text, int page, int size) {
        CompanyResponseView view = new CompanyResponseView();
        Pageable pageable = PageRequest.of(page-1,size);
        view.setCompanyResponses(companyResponses(companies(text,pageable)));
        return view;
    }

    private List<CompanyResponse> companyResponses(List<Company> companies) {
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company company : companies) {
            responses.add(mapper.mapToResponse(company));
        }
        return responses;
    }

    private List<Company> companies(String text, Pageable pageable){
        String name = text == null ? "" : text;
        return companyRepository.getAll(name.toUpperCase(), pageable);
    }

}
