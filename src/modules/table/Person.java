package modules.table;

/**
 * Created by AndreiM on 3/19/2017.
 */
public class Person {
    protected String identifier;
    protected String phone;

    public Person() {
        this.identifier = null;
        this.phone = null;
    }

    public Person (String identifier) {
        this.identifier = identifier;
        this.phone = null;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPhone() {
        return phone;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
