package modules.filter;

/**
 * Created by AndreiM on 3/23/2017.
 * Colour palette used by the app. Can be customized
 */
public enum Colors {
    LIGHTRED (255, 204, 204),
    LIGHTGREEN (220, 245, 220);

    private final int r;
    private final int g;
    private final int b;
    private final String rgb;

    Colors(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.rgb = r + ", " + g + ", " + b;
    }
    public String getRGB() {
        return rgb;
    }

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
