package creatures;

import io.micronaut.context.annotation.Prototype;
import java.util.ArrayList;
import utils.Coordinates;

@Prototype
public class DefenderAnt extends Ant {

    public DefenderAnt(int colonyIdentifier, Coordinates coordinates) {
        super(75, 75, new ArrayList<>(), 75, 75, 2, 150, colonyIdentifier, "D", coordinates);
    }

    @Override
    void carry() {}

    @Override
    void putDown() {}
}
