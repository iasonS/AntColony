package environment.game;

public class GameBoardParams {

    private static final int xMin = 0;
    private static final int yMin = 0;
    private static final int xMax = 2000;
    private static final int yMax = 2000;

    public static final int COLONY_COORD_BOUNDARY = 400;

    public static int getXMin() {
        return xMin;
    }

    public static int getYMin() {
        return yMin;
    }

    public static int getXMax() {
        return xMax;
    }

    public static int getYMax() {
        return yMax;
    }
}
