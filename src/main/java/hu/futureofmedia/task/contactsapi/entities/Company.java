package hu.futureofmedia.task.contactsapi.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company {
    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Contact> contacts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
