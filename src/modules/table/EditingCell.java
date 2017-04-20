package modules.table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modules.record_structures.CallRecord;

/**
     * Allows to edit and save TableView cells
     */
    public class EditingCell extends TableCell<CallRecord, String> {
        private TextField cellTextField;
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

            if( cellTextField == null ) {
                createTextField();
            }
            setText(null);
            setGraphic(cellTextField);
            cellTextField.selectAll();
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
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {

                if (isEditing()) {
                    if (cellTextField != null) {
                        cellTextField.setText(getString());
                    }
                    setText(null);
                    setGraphic(cellTextField);
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
            cellTextField = new TextField(getString());
            cellTextField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            cellTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                    if (!arg2) { commitEdit(cellTextField.getText()); }
                }
            });
            cellTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        String value = cellTextField.getText();
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