package creatures;

import io.micronaut.context.annotation.Prototype;
import java.util.ArrayList;
import utils.Coordinates;

@Prototype
public class WorkerAnt extends Ant {

    public WorkerAnt(int colonyIdentifier, Coordinates coordinates) {
        super(30, 50, new ArrayList<>(), 30, 115, 1, 25, colonyIdentifier, "W", coordinates);
    }

    @Override
    void carry() {}

    @Override
    void putDown() {}
}
