package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.entities.*;

import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import hu.futureofmedia.task.contactsapi.validation.ContactValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;


@Service
public class ContactService {

    private Logger logger = LoggerFactory.getLogger(ContactService.class);

    public final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    public Page<ContactDTO> getContacts(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 10, Sort.by("lastname"));
        Page<Contact> contactPage = contactRepository.findContactByStatus(Status.ACTIVE, paging);

        return contactPage.map(this::convertContactToContactDto);
    }


    private ContactDTO convertContactToContactDto(final Contact contact) {
        final ContactDTO contactDto = new ContactDTO(
                contact.getId(),
                contact.getFirstname(),
                contact.getLastname(),
                contact.getEmail(),
                convertCompanyToCompanyDTO(contact.getCompany()),
                contact.getTelephonenumber()
        );
        return contactDto;
    }

    private CompanyDTO convertCompanyToCompanyDTO(final Company company) {
        final CompanyDTO companyDTO = new CompanyDTO(
                company.getName()
        );
        return companyDTO;
    }


    public ResponseEntity<String> addContact(Errors errors, ContactSaveDTO contactSaveDTO, Company company) {
        if(ContactValidation.isEmptyField(errors)){
            return new ResponseEntity<>("Field(s) not empty", HttpStatus.FORBIDDEN);
        };
        Contact contact = convertContactSaveDTOToContact(contactSaveDTO, company);
        String validContactMessage = ContactValidation.checkValidContact(contactSaveDTO, company, contact);
        if (!validContactMessage.equals("Valid")) {
            return new ResponseEntity<>(validContactMessage, HttpStatus.FORBIDDEN);
        }
        contact.setStatus(Status.ACTIVE);
        contactRepository.save(contact);
        return new ResponseEntity<>("Save contact to database", HttpStatus.ACCEPTED);
    }


    private Contact convertContactSaveDTOToContact(ContactSaveDTO contactSaveDTO, Company company) {
        Contact contact = new Contact();
        contact.setLastname(contactSaveDTO.getLastname());
        contact.setFirstname(contactSaveDTO.getFirstname());
        contact.setEmail(contactSaveDTO.getEmail());
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
            return new ResponseEntity<>("Contact with id: " + contactId + "not found", HttpStatus.NOT_FOUND);
        }
        Contact contact = contactRepository.findContactById(contactId);
        contact.setStatus(Status.DELETED);
        contactRepository.save(contact);
        return new ResponseEntity<>("Contact "+ contactId + " deleted", HttpStatus.RESET_CONTENT);
    }
}
