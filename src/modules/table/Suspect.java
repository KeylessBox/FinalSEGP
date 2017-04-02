package modules.table;

import java.awt.*;

/**
 * Created by AndreiM on 3/23/2017.
 */
public class Suspect extends Person{
    private Color color = new Color(Colors.LIGHTRED.getRed(),Colors.LIGHTRED.getGreen(),Colors.LIGHTRED.getBlue());

    public Suspect (String identifier) {
        super.identifier = identifier;
        super.phone = null;
    }

    public Suspect (String identifier, String id) {
        super.identifier = identifier;
        super.phone = null;
        super.id = id;
    }

    public Color getColor() {
        return color;
    }
}
