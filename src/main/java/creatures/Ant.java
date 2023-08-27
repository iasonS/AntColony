package creatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.Coordinates;

@AllArgsConstructor
@Getter
public abstract class Ant {

    protected int health;
    protected int food;
    protected List<Object> carryItems;
    protected int carryWeight;
    protected int moveSpeed;
    protected int size;
    protected int biteStr;
    protected int colonyIdentifier;
    protected String antGraphic;
    protected Coordinates currentCoordinates;

    // Moves towards the specific coordinates given its speed
    public Coordinates move(Coordinates targetCoordinates, Set<Coordinates> blockedCoordinates) {
        if (targetCoordinates.equals(currentCoordinates)) {
            return targetCoordinates;
        }

        List<Coordinates> path = findPath(currentCoordinates, targetCoordinates, blockedCoordinates);
        if (!path.isEmpty()) {
            Coordinates nextStep = path.get(0);
            currentCoordinates = nextStep;
            System.out.println("Ant moved to: " + nextStep);
        } else {
            System.out.println("Ant can't find path to move");
            return null;
        }
        return path.get(0);
    }

    // TODO test this
    private List<Coordinates> findPath(Coordinates start, Coordinates target, Set<Coordinates> blocked) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = start;

        while (!current.equals(target)) {
            int nextX = current.getX() + Integer.compare(target.getX(), current.getX());
            int nextY = current.getY() + Integer.compare(target.getY(), current.getY());
            Coordinates nextStep = new Coordinates(nextX, nextY);

            if (!blocked.contains(nextStep)) {
                path.add(nextStep);
                current = nextStep;
            } else {
                break; // Can't proceed due to blocked coordinates
            }
        }

        return path;
    }


    // TODO move random without blocking
    public void moveRandom(){

    }

    abstract void carry();

    abstract void putDown();

    void eat(int foodValue) {
        food = Math.min(foodValue + food, 100);
    }
}
