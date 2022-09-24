package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.CompanyResponse;

import java.util.List;

@Getter
@Setter
public class CompanyResponseView {

    private List<CompanyResponse> companyResponses;
}
