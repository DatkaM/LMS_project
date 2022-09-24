package peaksoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CompanyRequest;
import peaksoft.dto.response.CompanyResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.search.CompanyResponseView;
import peaksoft.service.CompanyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/company")
@PreAuthorize("hasAuthority('ADMIN')")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public CompanyResponse saveCompany(@RequestBody CompanyRequest request) {
        return companyService.saveCompany(request);
    }

    @GetMapping("{id}")
    public CompanyResponse getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @DeleteMapping("{id}")
    public SimpleResponse deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompanyById(id);
    }

    @PutMapping("{id}")
    public CompanyResponse updateCompany(@PathVariable Long id,
                                         @RequestBody CompanyRequest request) {
        return companyService.updateCompany(id, request);
    }

    @GetMapping
    public CompanyResponseView searchByCompanyName(@RequestParam(name = "text",required = false) String text,
                                                   @RequestParam int page,
                                                   @RequestParam int size){
        return companyService.searchByCompanyName(text,page,size);
    }
}
