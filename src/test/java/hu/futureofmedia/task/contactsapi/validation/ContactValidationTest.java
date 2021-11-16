package hu.futureofmedia.task.contactsapi.validation;

import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactValidationTest {

    @Autowired
    public ContactRepository contactRepository;

    @Test
    public void validEmailTest(){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean validEmail = ContactValidation.patternMatches("test@gmail.com", regex);
        assertTrue(validEmail);
    }

    @Test
    public void inValidTest1(){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean validEmail = ContactValidation.patternMatches("testgmail.com", regex);
        assertFalse(validEmail);
    }

    @Test
    public void inValidTest2(){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean validEmail = ContactValidation.patternMatches("test@gmailcom", regex);
        assertFalse(validEmail);
    }
}