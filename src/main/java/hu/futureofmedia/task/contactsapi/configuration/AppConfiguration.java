package hu.futureofmedia.task.contactsapi.configuration;


import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
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
    @Profile("production")
    CommandLineRunner commandLineRunner(ContactRepository contactRepository, CompanyRepository companyRepository) {
        return args -> {
            List<Company> companys = companyRepository.findAll();
            Company company1 = companys.get(0);

            Contact contact1 = new Contact();
            contact1.setFirstname("Béla");
            contact1.setLastname("Tóth");
            contact1.setEmail("bela.toth@gmail.com");
            contact1.setTelephonenumber("06101113333");
            contact1.setCompany(company1);
            contact1.setStatus(Status.ACTIVE);
            contact1.setCreateDate(LocalDateTime.now());
            contact1.setLastModified(LocalDateTime.now());
            contactRepository.save(contact1);


        };
    }
}
