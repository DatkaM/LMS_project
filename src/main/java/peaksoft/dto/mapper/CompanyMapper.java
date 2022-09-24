package peaksoft.dto.mapper;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import peaksoft.dto.request.CompanyRequest;
import peaksoft.dto.response.CompanyResponse;
import peaksoft.entity.Company;
import peaksoft.repositories.CompanyRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper {


    public Company mapToEntity(CompanyRequest request){
        return Company.builder()
                .companyName(request.getCompanyName())
                .locatedCountry(request.getLocatedCountry())
                .build();
    }

    public CompanyResponse mapToResponse(Company company){
        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .locatedCountry(company.getLocatedCountry())
                .build();
    }

}
