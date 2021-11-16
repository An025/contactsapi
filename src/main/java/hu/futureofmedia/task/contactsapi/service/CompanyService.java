package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Service
public class CompanyService {
    public final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company getCompany(Long id){
        boolean exists = companyRepository.existsById(id);
        if(exists){
            return companyRepository.findById(id).get();
        }else{
            return null;
        }

    }


}
