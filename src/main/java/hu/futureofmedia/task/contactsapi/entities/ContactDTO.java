package hu.futureofmedia.task.contactsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ContactDTO {
    private String fullname;
    private CompanyDTO company;
    private String email;
    private String telephonNumber;

    public ContactDTO() {
    }

    public ContactDTO(String firstname, String lastname, String email, hu.futureofmedia.task.contactsapi.entities.CompanyDTO company, String telephonNumber) {
        this.fullname = firstname + " " + lastname;
        this.email = email;
        this.company = company;
        this.telephonNumber = telephonNumber;
    }

    public String getTelephonNumber() {
        return telephonNumber;
    }

    public void setTelephonNumber(String telephonNumber) {
        this.telephonNumber = telephonNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }
}
