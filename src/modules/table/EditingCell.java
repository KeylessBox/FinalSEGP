package modules.table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by AndreiM on 2/3/2017.
 */

    /**
     * EditingCell class allows to edit and save TableView cells
     */
    public class EditingCell extends TableCell<CallRecord, String> {

        private TextField textField;

        /**
         * Empty constructor
         */
        public EditingCell() {
        }

        /**
         * Start editing table cell
         */
        @Override
        public void startEdit() {
            super.startEdit();

            if( textField == null ) {
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

        /**
         * Update item with new value
         * @param item
         * @param empty
         */
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
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
                    if (!arg2) { commitEdit(textField.getText()); }
                }
            });
            textField.setAlignment(Pos.CENTER_LEFT);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        String value = textField.getText();
                        if (value != null) { commitEdit(value);	} else { commitEdit(null); }
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        /**
         * Return String if not null otherwise empty String
         * @return
         */
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }