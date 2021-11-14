package hu.futureofmedia.task.contactsapi.controllers;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.ContactDTO;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="api/v1/contacts")
public class ContacController {

    private final ContactService contactService;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    public ContacController(ContactService contactService){
        this.contactService = contactService;
    }

    //List pageNo 10 active contacts
    @GetMapping(path="list")
    public ResponseEntity<Page<ContactDTO>> getContacts(@RequestParam(defaultValue = "0") Integer pageNo){
        logger.info("List all contacts");
        return new ResponseEntity<>(contactService.getContacts(pageNo), HttpStatus.OK);
    }

    //List selected contact
    @GetMapping(path="list/{contactId}")
    public ResponseEntity<Contact> getContact(@PathVariable("contactId") Long contactId){
        logger.info("Display details of contact with id: " + contactId);
        return new ResponseEntity<>(contactService.getSelectedContact(contactId), HttpStatus.OK);
    }

    @DeleteMapping(path="/delete/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable("contactId") Long contactId){
        logger.info("Requesting delete contact with id: " + contactId);
        return contactService.deleteContact(contactId);
    }

    @PostMapping(path="add")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
        return new ResponseEntity<>(contactService.addContact(contact), HttpStatus.OK);
    }
}
