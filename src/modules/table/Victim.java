package modules.table;

import java.awt.*;

/**
 * Created by AndreiM on 3/23/2017.
 */
public class Victim extends Person{
    private Color color = new Color(Colors.LIGHTGREEN.getRed(),Colors.LIGHTGREEN.getGreen(),Colors.LIGHTGREEN.getBlue());

    public Victim (String identifier) {
        super.identifier = identifier;
        super.phone = null;
    }

    public Color getColor() {
        return color;
    }
}
