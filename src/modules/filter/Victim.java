package modules.filter;

import java.awt.*;

/**
 * Created by AndreiM on 3/23/2017.
 * The victim is the green coloured filter
 */
public class Victim extends Person {
    private Color color = new Color(Colors.LIGHTGREEN.getRed(),Colors.LIGHTGREEN.getGreen(),Colors.LIGHTGREEN.getBlue());

    public Victim (String identifier) {
        super.identifier = identifier;
        super.phone = null;
    }

    public Victim (String identifier, String id) {
        super.previousIdentifier = super.identifier;
        super.identifier = identifier;
        super.phone = null;
        super.id = id;
    }
    public Color getColor() {
        return color;
    }
}
