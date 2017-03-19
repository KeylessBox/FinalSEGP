package modules.table;

/**
 * Created by AndreiM on 3/19/2017.
 */
public class Person {
    private String identifier;
    private String phone;

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
