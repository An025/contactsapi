package hu.futureofmedia.task.contactsapi.entities;

import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Table(name="contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}

