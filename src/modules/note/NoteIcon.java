package modules.note;

import javafx.scene.layout.Pane;

/**
 * Created by AndreiM on 3/13/2017.
 * Used to show the notes on the right side of the app
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
