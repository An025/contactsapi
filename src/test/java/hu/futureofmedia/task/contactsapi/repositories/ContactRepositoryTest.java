package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactRepositoryTest {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired CompanyRepository companyRepository;

    @Test
    @Order(1)
    public void saveContactTest(){
        List<Company> companys = companyRepository.findAll();
        Company company1 = companys.get(0);

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

        assertThat(contact1.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getContactTest(){
        Contact contact = contactRepository.findContactById(1L);
        assertThat(contact.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfContact(){
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateContactTest(){
        Contact contact = contactRepository.findContactById(1L);
        contact.setEmail("test@gmail.com");
        Contact contactUpdated = contactRepository.save(contact);
        assertThat(contactUpdated.getEmail()).isEqualTo("test@gmail.com");
    }
}