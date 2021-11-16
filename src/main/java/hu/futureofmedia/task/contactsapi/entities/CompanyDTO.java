package hu.futureofmedia.task.contactsapi.entities;

public class CompanyDTO {
    private String name;

    public CompanyDTO(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
