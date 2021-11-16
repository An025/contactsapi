package hu.futureofmedia.task.contactsapi.configuration;


import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Profile("production")
    CommandLineRunner commandLineRunner(ContactRepository contactRepository, CompanyRepository companyRepository) {
        return args -> {
            if(contactRepository.findAll().size() ==0) {
                List<Company> companys = companyRepository.findAll();
                Company company1 = companys.get(0);
//
                Contact contact1 = new Contact();
                contact1.setFirstname("Béla");
                contact1.setLastname("Tóth");
                contact1.setEmail("bela.toth@gmail.com");
                contact1.setTelephonenumber("+36101113333");
                contact1.setCompany(company1);
                contact1.setStatus(Status.ACTIVE);
                contact1.setCreateDate(LocalDateTime.now());
                contact1.setLastModified(LocalDateTime.now());
                contactRepository.save(contact1);

                Contact contact2 = new Contact();
                contact2.setFirstname("Lili");
                contact2.setLastname("Takács");
                contact2.setEmail("lili.takacs@gmail.com");
                contact2.setTelephonenumber("+36101113333");
                contact2.setCompany(company1);
                contact2.setStatus(Status.ACTIVE);
                contact2.setCreateDate(LocalDateTime.now());
                contact2.setLastModified(LocalDateTime.now());
                contactRepository.save(contact2);

                Contact contact3 = new Contact();
                contact3.setFirstname("László");
                contact3.setLastname("Szakál");
                contact3.setEmail("laszlo.szakal@gmail.com");
                contact3.setTelephonenumber("+36101113333");
                contact3.setCompany(company1);
                contact3.setStatus(Status.DELETED);
                contact3.setCreateDate(LocalDateTime.now());
                contact3.setLastModified(LocalDateTime.now());
                contactRepository.save(contact3);
            }
        };
    }
}
