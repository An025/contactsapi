package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.entities.*;

import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;


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

    public Contact addContact(Contact contact){
        return contactRepository.save(contact);
    }

    public Contact getSelectedContact(Long contactId) {
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if(contactOptional.isPresent()){
            return contactOptional.get();
        }else{
            throw new IllegalArgumentException("Not found contact: " + contactId);
        }
    }
}
