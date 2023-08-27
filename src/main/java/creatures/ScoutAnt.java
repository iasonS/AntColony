package creatures;

import io.micronaut.context.annotation.Prototype;
import java.util.ArrayList;
import utils.Coordinates;

@Prototype
public class ScoutAnt extends Ant {

    public ScoutAnt(int colonyIdentifier, Coordinates coordinates) {
        super(20, 50, new ArrayList<>(), 15, 150, 1, 15, colonyIdentifier, "S", coordinates);
    }

    @Override
    void carry() {}

    @Override
    void putDown() {}
}
