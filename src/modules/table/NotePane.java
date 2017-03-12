package modules.table;

import javafx.scene.layout.Pane;

/**
 * Created by AndreiM on 3/12/2017.
 */
public class NotePane extends Pane {
    private boolean isOpen = false;
    private int noteID;


    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }
    public int getNoteID() {
        return noteID;
    }

    public void setOpen(boolean change) {
        isOpen = change;
    }

    public boolean getOpen() {
        return isOpen;
    }
}
