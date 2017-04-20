package modules.filter;

/**
 * Created by AndreiM on 3/19/2017.
 * A filter is considered a person
 */
public class Person {
    protected String identifier;
    protected String phone;
    protected String id;
    protected String previousIdentifier;

    public Person() {
        this.identifier = null;
        this.phone = null;
        this.id = null;
        this.previousIdentifier = null;
    }

    public Person (String identifier) {
        this.previousIdentifier = this.getIdentifier();
        this.identifier = identifier;
        this.phone = null;
        this.id = null;
    }

    public Person (String identifier, String id) {
        this.previousIdentifier = this.getIdentifier();
        this.identifier = identifier;
        this.phone = null;
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPreviousIdentifier() {
        return previousIdentifier;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setIdentifier(String identifier) {
        this.previousIdentifier = this.getIdentifier();
        this.identifier = identifier;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }
}
