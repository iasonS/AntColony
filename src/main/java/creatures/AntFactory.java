package creatures;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Singleton;
import utils.Coordinates;

@Singleton
@Factory
public class AntFactory {

    public Ant createAnt(
            Class<? extends Ant> antType, int colonyIdentifier, Coordinates coordinates) {
        if (antType.equals(QueenAnt.class)) {
            return createQueenAnt(colonyIdentifier, coordinates);
        } else if (antType.equals(DefenderAnt.class)) {
            return createDefenderAnt(colonyIdentifier, coordinates);
        } else if (antType.equals(WorkerAnt.class)) {
            return createWorkerAnt(colonyIdentifier, coordinates);
        } else if (antType.equals(ScoutAnt.class)) {
            return createScoutAnt(colonyIdentifier, coordinates);
        }
        throw new IllegalArgumentException("Unsupported ant type: " + antType.getName());
    }

    @Prototype
    public QueenAnt createQueenAnt(int colonyIdentifier, Coordinates coordinates) {
        return new QueenAnt(colonyIdentifier, coordinates);
    }

    @Prototype
    public DefenderAnt createDefenderAnt(int colonyIdentifier, Coordinates coordinates) {
        return new DefenderAnt(colonyIdentifier, coordinates);
    }

    @Prototype
    public WorkerAnt createWorkerAnt(int colonyIdentifier, Coordinates coordinates) {
        return new WorkerAnt(colonyIdentifier, coordinates);
    }

    @Prototype
    public ScoutAnt createScoutAnt(int colonyIdentifier, Coordinates coordinates) {
        return new ScoutAnt(colonyIdentifier, coordinates);
    }
}
