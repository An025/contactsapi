package hu.futureofmedia.task.contactsapi.entities;

import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname;
    private String firstname;

    @Email(message= "Email should be valid")
    private String email;

    //TODO validaate phonenumber with Libphonenumber
    private String telephonenumber;
    private String comment;

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne
    private Company company;

    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createDate;
    private LocalDateTime lastModified;

    public Contact() {
        this.createDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    public Contact(Long id,String lastname, String firstname, String email, String telephonenumber, String comment, Company company, Status status, LocalDateTime createDate, LocalDateTime lastModified) {
        this.id= id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.telephonenumber = telephonenumber;
        this.comment = comment;
        this.company = company;
        this.status = status;
        this.createDate = createDate;
        this.lastModified = lastModified;
    }

    public Long getId() {
        return id;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", telephonenumber='" + telephonenumber + '\'' +
                ", comment='" + comment + '\'' +
                ", company=" + company +
                ", status=" + status +
                ", createDate=" + createDate +
                ", lastModified=" + lastModified +
                '}';
    }
}

