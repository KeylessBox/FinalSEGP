package modules.table;

import javafx.scene.layout.Pane;

/**
 * Created by AndreiM on 3/13/2017.
 */
public class NoteIcon extends Pane {

    private boolean isOpen = false;

    public void changeOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    public boolean isOpen() {
        return isOpen;
    }
}
