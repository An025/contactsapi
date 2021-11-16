package hu.futureofmedia.task.contactsapi.repositories;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactRepository extends JpaRepository<Contact, Long> {

//    @Query(value ="SELECT c FROM Contact c JOIN c.company company WHERE c.status =:status ")
//    List<Contact> findContactsByStatus(@Param("status") Status status);

    Page<Contact> findContactByStatus(Status status, Pageable paging);


    Contact findContactById(Long contactId);
}
