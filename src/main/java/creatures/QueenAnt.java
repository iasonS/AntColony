package creatures;

import io.micronaut.context.annotation.Prototype;
import java.util.ArrayList;
import utils.Coordinates;

@Prototype
public class QueenAnt extends Ant {

    public QueenAnt(int colonyIdentifier, Coordinates coordinates) {
        super(100, 100, new ArrayList<>(), 50, 100, 3, 50, colonyIdentifier, "Q", coordinates);
    }

    @Override
    void carry() {}

    @Override
    void putDown() {}
}
