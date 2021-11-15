package hu.futureofmedia.task.contactsapi.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.ContactSaveDTO;
import org.springframework.validation.Errors;

import java.util.regex.Pattern;

public  class ContactValidation {

    //check empty fields
    public static boolean isEmptyField(Errors errors){
        if(errors.hasErrors()) {
            return true;
        }
        return false;
    }


    public  static String checkValidContact(ContactSaveDTO contactSaveDTO, Company company, Contact contact) {
        //Check email validation
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean validEmail = patternMatches(contact.getEmail(), regex);
        if (!validEmail) {
            return "Not valid email";
        }
        //Check phonenumber validation
        if (contact.getTelephonenumber() != null) {
            boolean validPhoneNumber = checkPhoneNumber(contact.getTelephonenumber());
            if (!validPhoneNumber) {
                return "Not a valid phonenumber";
            }
        }
        return "Valid";
    }


    private static boolean checkPhoneNumber(String telephonenumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try{
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(telephonenumber, "HU");
            String formattedNumber = phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
            if(!formattedNumber.equals(telephonenumber)){
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return true;
    }

    //Check Email validation
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
