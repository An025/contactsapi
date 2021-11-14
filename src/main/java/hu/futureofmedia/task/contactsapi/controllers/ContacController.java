package hu.futureofmedia.task.contactsapi.controllers;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.ContactDTO;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="api/v1")
public class ContacController {

    private final ContactService contactService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ContacController(ContactService contactService){
        this.contactService = contactService;
    }

    //List pageNo 10 active contacts
    @GetMapping(path="contact")
    public ResponseEntity<Page<ContactDTO>> getContacts(@RequestParam(defaultValue = "0") Integer pageNo){
        return new ResponseEntity<>(contactService.getContacts(pageNo), HttpStatus.OK);
    }

    //List selected contact
    @GetMapping(path="contact/{contactId}")
    public Contact getContact(@PathVariable("contactId") Long contactId){
        return contactService.getSelectedContact(contactId);
    }

    @PostMapping(path="contact")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
        return new ResponseEntity<>(contactService.addContact(contact), HttpStatus.OK);
    }
}