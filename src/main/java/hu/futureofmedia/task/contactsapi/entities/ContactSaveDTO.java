package hu.futureofmedia.task.contactsapi.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


public class ContactSaveDTO {
    private Long id;

    @NotEmpty(message = "Lastname may not be empty")
    private String lastname;

    @NotEmpty(message = "Firstname may not be empty")
    private String firstname;

//    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email may not be empty")
    private String email;

    //TODO validaate phonenumber with Libphonenumber
    private String telephonenumber;

    private String comment;


//    @NotEmpty(message = "Company may not be empty")
    private String company;

    public ContactSaveDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephonenumber() {
        return telephonenumber;
    }

    public void setTelephonenumber(String telephonenumber) {
        this.telephonenumber = telephonenumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
