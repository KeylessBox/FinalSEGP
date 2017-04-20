package modules.table;

import controllers.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modules.filter.Suspect;
import modules.filter.Victim;
import modules.record_structures.CallRecord;

import java.util.List;

/**
 * Created by AndreiM on 3/23/2017.
 * Used for the columns that hold phone numbers
 */
public class EditingPhoneCell extends TableCell<CallRecord, String> {
    private TextField textField;

    /**
     * Empty constructor
     */
    public EditingPhoneCell() {
    }

    /**
     * Start editing table cell
     */
    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    /**
     * Cancel editing table cell
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem());
        setGraphic(null);
    }

    //TODO What happens when 2 filters overlap?
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            setStyle("");
        } else {
            // Get filters from app
            List<Object[]> people = MainController.getPeople();
            boolean isFilter = false;
            // Iterates through filters and based on the nature of the object, it colours the cell in a specific colour
            for (Object[] person : people) {
                if (person[1].equals("yes")) {
                    if (person[0] instanceof Suspect) {
                        Suspect suspect = (Suspect) person[0];
                        if (item.contains(suspect.getPhone())) {
                            String hex = Integer.toHexString(suspect.getColor().getRGB());
                            // Reduced to RGB: hex -> "#ff0000"
                            hex = "#" + hex.substring(2, hex.length());
                            setStyle("-fx-background-color: " + hex + ";" +
                                    "-fx-border-width: 0 1 1 0;" +
                                    " -fx-background-insets: 0;" +
                                    "-fx-border-color: black");
                            isFilter = true;
                        }
                    }
                    if (person[0] instanceof Victim) {
                        Victim victim = (Victim) person[0];
                        if (item.contains(victim.getPhone())) {
                            String hex = Integer.toHexString(victim.getColor().getRGB());
                            // Reduced to RGB: hex -> "#ff0000"
                            hex = "#" + hex.substring(2, hex.length());
                            setStyle("-fx-background-color: " + hex + ";" +
                                    "-fx-border-width: 0 1 1 0;" +
                                    "-fx-border-color: black;" +
                                    " -fx-background-insets: 0;");
                            isFilter = true;
                        }
                    }
                }
            }
            if (!isFilter) {
                setStyle("");
            }
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    /**
     * Create textfield to save changes after
     */
    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                if (!arg2) {
                    commitEdit(textField.getText());
                }
            }
        });
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    String value = textField.getText();
                    if (value != null) {
                        commitEdit(value);
                    } else {
                        commitEdit(null);
                    }
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    /**
     * Return String if not null otherwise empty String
     *
     * @return
     */
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
