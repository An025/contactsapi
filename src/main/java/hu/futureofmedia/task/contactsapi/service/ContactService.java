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

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ContactService {

    private Logger logger = LoggerFactory.getLogger(ContactService.class);

    public final ContactRepository contactRepository;
    public final CompanyService companyService;

    public ContactService(ContactRepository contactRepository, CompanyService companyService) {
        this.contactRepository = contactRepository;
        this.companyService = companyService;
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


    private Contact convertContactSaveDTOToContact(Contact contact, ContactSaveDTO contactSaveDTO, Company company) {
        contact.setLastname(contactSaveDTO.getLastname());
        contact.setFirstname(contactSaveDTO.getFirstname());
        contact.setEmail(contactSaveDTO.getEmail());
        contact.setTelephonenumber(contactSaveDTO.getTelephonenumber());
        contact.setComment(contactSaveDTO.getComment());
        contact.setCompany(company);
        contact.setStatus(Status.ACTIVE);
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


    public ResponseEntity<String> saveOrUpdateContact(ContactSaveDTO contactSaveDTO, Contact contact) {
        String validMessage = ContactValidation.validContactData(contactSaveDTO);
        if(!validMessage.equals("Valid")){
            return new ResponseEntity<>(validMessage, HttpStatus.FORBIDDEN);
        }
        // Check contact is exists if exists Update data else create new contact
        Company company = getCompanyFromContactSaveDTO(contactSaveDTO);
        contact.setLastModified(LocalDateTime.now());
        //Received contact(contactSaveDTO) convert contact
        contact = convertContactSaveDTOToContact(contact, contactSaveDTO, company);
        contactRepository.save(contact);
        return new ResponseEntity<>("Create/Update contact to database", HttpStatus.ACCEPTED);
    }


    public Contact getExistsContact(ContactSaveDTO contactSaveDTO){
        if(!contactRepository.existsById(contactSaveDTO.getId())){
            return null;
        }
        return contactRepository.findContactById(contactSaveDTO.getId());
    }


    private Company getCompanyFromContactSaveDTO(ContactSaveDTO contactSaveDTO){
        Long companyId = Long.parseLong(contactSaveDTO.getCompany());
        return companyService.getCompany(companyId);
    }
}
