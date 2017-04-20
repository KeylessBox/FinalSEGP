package modules.filter;

/**
 * Created by AndreiM on 3/23/2017.
 * Search bar object
 */
public class SearchField {
    private String text;

    public SearchField(String text) {
        this.text = text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
