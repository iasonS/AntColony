package utils;

import environment.game.GameBoardParams;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Coordinates {
    private final int x;
    private final int y;

    public int distanceTo(Coordinates other) {
        int deltaX = other.x - this.x;
        int deltaY = other.y - this.y;
        return (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public static Coordinates generateNewRandomCoordinatesWithExclusions(
            Set<Coordinates> coordinatesList) {
        Random random = new Random();

        int rangeX = GameBoardParams.getXMax() - GameBoardParams.getXMin();
        int rangeY = GameBoardParams.getYMax() - GameBoardParams.getYMin();

        Coordinates coordinates;
        do {
            int xCoord = random.nextInt(rangeX) + GameBoardParams.getXMin();
            int yCoord = random.nextInt(rangeY) + GameBoardParams.getYMin();
            coordinates = new Coordinates(xCoord, yCoord);
        } while (hasCoordinatesWithinRange(coordinatesList, coordinates));

        return coordinates;
    }

    private static boolean hasCoordinatesWithinRange(
            Set<Coordinates> coordinatesList, Coordinates targetCoordinates) {
        for (Coordinates c : coordinatesList) {
            if (comparisonCoordinatesIsAtLeast200UnitsAway(targetCoordinates, c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean comparisonCoordinatesIsAtLeast200UnitsAway(
            Coordinates c1, Coordinates c2) {
        int deltaX = c1.getX() - c2.getY();
        int deltaY = c1.getY() - c2.getY();
        int distanceSquared = deltaX * deltaX + deltaY * deltaY;
        return distanceSquared < 200 * 200;
    }

    @Override
    public boolean equals(Object coordinates) {
        if (this == coordinates) return true;
        if (coordinates == null || getClass() != coordinates.getClass()) return false;
        Coordinates otherCoordinates = (Coordinates) coordinates;
        return x == otherCoordinates.x && y == otherCoordinates.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("Coordinate x: %s, Coordinate y: %s", x, y);
    }
}
