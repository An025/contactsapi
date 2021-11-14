package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.entities.*;

import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ContactService {

    private Logger logger = LoggerFactory.getLogger(ContactService.class);

    public final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }



    public Page<ContactDTO> getContacts(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 10, Sort.by("lastname"));
            Page<Contact> contactPage = contactRepository.findContactByStatus(Status.ACTIVE, paging);

            final Page<ContactDTO> contactDtoPage = contactPage.map(this::convertToContactDto);
            return contactDtoPage;
        };


    private ContactDTO convertToContactDto(final Contact contact) {
        final ContactDTO contactDto = new ContactDTO(
                contact.getId(),
                contact.getFirstname(),
                contact.getLastname(),
                contact.getEmail(),
                convertToCompanyDto(contact.getCompany()),
                contact.getTelephonenumber()
        );
        return contactDto;
    }

    private CompanyDTO convertToCompanyDto(final Company company) {
        final CompanyDTO companyDTO = new CompanyDTO(
                company.getName()
        );
        return companyDTO;
    }

    public Contact addContact(ContactSaveDTO contactSaveDTO, Company company){
        Contact contact = convertContact(contactSaveDTO, company);

        contact.setStatus(Status.ACTIVE);
        return contactRepository.save(contact);
    }

    public Contact convertContact(ContactSaveDTO contactSaveDTO, Company company){
        Contact contact = new Contact();
        contact.setLastname(contactSaveDTO.getLastname());
        contact.setFirstname(contactSaveDTO.getFirstname());
        contact.setEmail(contactSaveDTO.getTelephonenumber());
        contact.setTelephonenumber(contactSaveDTO.getTelephonenumber());
        contact.setComment(contactSaveDTO.getComment());
        contact.setCompany(company);
        return contact;
    }

    public Contact getSelectedContact(Long contactId) {
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if(contactOptional.isPresent()){
            return contactOptional.get();
        }else{
            throw new IllegalArgumentException("Not found contact: " + contactId);
        }
    }

    public ResponseEntity<String> deleteContact(Long contactId) {
        boolean exists = contactRepository.existsById(contactId);
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact with id: " + contactId + "not found");
        }
        Contact contact = contactRepository.findById(contactId).get();
        contact.setStatus(Status.DELETED);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).body("Contact "+ contactId + " deleted");
    }
}
