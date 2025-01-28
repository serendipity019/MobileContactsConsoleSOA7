package gr.aueb.cf.mobile_contacts.model;

public abstract class AbstractEntity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
