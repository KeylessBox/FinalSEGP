package modules.table;

import controllers.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

/**
 * Created by AndreiM on 4/3/2017.
 */
public class EditingDestNameCell extends TableCell<CallRecord, String>{

    /**
     * Created by AndreiM on 3/23/2017.
     */
    private TextField textField;

    /**
     * Empty constructor
     */
    public EditingDestNameCell() {
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
            List<Object[]> people = MainController.getPeople();
            boolean isFilter = false;
            for (Object[] temp : people) {
                if (temp[1].equals("yes")) {
                    Person person = (Person) temp[0];
                    CallRecord listObject = (CallRecord) getTableRow().getItem();
                    if (listObject != null) {
                        if (listObject.getDestination().contains(person.getPhone())) {
                            System.out.println("B " + person.getIdentifier());
                            setText(person.getIdentifier());

                            System.out.println("TRUE");
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
