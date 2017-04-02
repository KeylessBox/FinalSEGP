package modules.table;

import javafx.scene.paint.Color;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Created by AndreiM on 3/23/2017.
 */
public enum Colors {
    LIGHTRED (255, 204, 204),
    LIGHTGREEN (230,255,230);

    private final int r;
    private final int g;
    private final int b;
    private final String rgb;

    private Colors(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.rgb = r + ", " + g + ", " + b;
    }
    public String getRGB() {
        return rgb;
    }

    //You can add methods like this too
    public int getRed(){
        return r;
    }

    public int getGreen(){
        return g;
    }

    public int getBlue(){
        return b;
    }

}
