package hu.futureofmedia.task.contactsapi.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import hu.futureofmedia.task.contactsapi.entities.ContactSaveDTO;
import java.util.regex.Pattern;

public  class ContactValidation {

    public static String validContactData(ContactSaveDTO contactSaveDTO) {
        //Check field null or empty
        if (isFieldNull(contactSaveDTO) || isEmptyField(contactSaveDTO)){
            return "Field(s) can't be empty";
        }
        //Check email validation
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean validEmail = patternMatches(contactSaveDTO.getEmail(), regex);
        if (!validEmail) {
            return "Not valid email";
        }
        //If phonenumber exists check phonenumber id valid(E164)
        if (contactSaveDTO.getTelephonenumber() != null) {
            boolean validPhoneNumber = checkPhoneNumber(contactSaveDTO.getTelephonenumber());
            if (!validPhoneNumber) {
                return "Not a valid phonenumber";
            }
        }
        return "Valid";
    }


    private static boolean isFieldNull (ContactSaveDTO contactSaveDTO) {
        return (contactSaveDTO.getLastname() == null || contactSaveDTO.getFirstname() == null || contactSaveDTO.getEmail() == null
                || contactSaveDTO.getCompany() == null);
    }


    private static boolean isEmptyField (ContactSaveDTO contactSaveDTO){
        return contactSaveDTO.getLastname().equals("") || contactSaveDTO.getFirstname().equals("") || contactSaveDTO.getEmail().equals("")
                || contactSaveDTO.getCompany().equals("");
    }


    private static boolean checkPhoneNumber (String telephonenumber){
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(telephonenumber, "HU");
            String formattedNumber = phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
            if (!formattedNumber.equals(telephonenumber)) {
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return true;
    }

    //Check Email validation
    public static boolean patternMatches (String emailAddress, String regexPattern){
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}