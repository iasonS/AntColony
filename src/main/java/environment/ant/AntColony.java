package environment.ant;

import creatures.*;
import environment.Food;
import environment.pheromone.Pheromone;
import io.micronaut.context.annotation.Prototype;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import utils.Coordinates;

@Slf4j
@Prototype
public class AntColony {

    private final Map<Integer, Ant> colonyAntsMap = new HashMap<>();
    private final Map<Integer, QueenAnt> queenAntsMap = new HashMap<>();
    private final Map<Integer, DefenderAnt> defenderAntsMap = new HashMap<>();
    private final Map<Integer, WorkerAnt> workerAntsMap = new HashMap<>();
    private final Map<Integer, ScoutAnt> scoutAntsMap = new HashMap<>();
    @Getter private final int uniqueIdentifier;
    private Map<Coordinates, AntNest> antNests;
    private final AntFactory antFactory;

    public AntColony(int uniqueIdentifier, AntFactory antFactory, Coordinates initNestCoords) {
        this.uniqueIdentifier = uniqueIdentifier;
        antNests.put(initNestCoords, new AntNest(initNestCoords, new ArrayList<>(), new Food(100)));
        queenAntsMap.putAll(createAnts(QueenAnt.class, 1, uniqueIdentifier, initNestCoords));
        defenderAntsMap.putAll(createAnts(DefenderAnt.class, 5, uniqueIdentifier, initNestCoords));
        workerAntsMap.putAll(createAnts(WorkerAnt.class, 10, uniqueIdentifier, initNestCoords));
        scoutAntsMap.putAll(createAnts(ScoutAnt.class, 5, uniqueIdentifier, initNestCoords));
        this.antFactory = antFactory;
    }

    public List<Ant> provideAntsToMove(){
        return new ArrayList<>(colonyAntsMap.values());
    }

    private <T extends Ant> Map<Integer, T> createAnts(
            Class<T> antType, int numberOfAnts, int uniqueIdentifier, Coordinates initNestCoords) {
        Map<Integer, T> ants = new HashMap<>();
        try {
            for (int i = 0; i < numberOfAnts; i++) {
                T antInstance = (T) antFactory.createAnt(antType, uniqueIdentifier, initNestCoords);
                ants.put(antInstance.hashCode(), antInstance);
            }
        } catch (Exception e) {
            log.warn("Could not generate ant type: {}", antType);
        }
        colonyAntsMap.putAll(ants);
        return ants;
    }

    @Override
    public String toString() {
        String queenStatus = !queenAntsMap.isEmpty() ? "alive" : "dead";

        return "Colony Details:\n"
                + "+------------------+-----------------+------------------+-----------------+----------------+\n"
                + "| Colony ID        | Queen Status    | Defender Ants    | Worker Ants     | Scout Ants     |\n"
                + "+------------------+-----------------+------------------+-----------------+----------------+\n"
                + String.format(
                        "| %-16d | %-15s | %-16d | %-15d | %-15d |\n",
                        uniqueIdentifier,
                        queenStatus,
                        defenderAntsMap.size(),
                        workerAntsMap.size(),
                        scoutAntsMap.size())
                + "+------------------+-----------------+------------------+-----------------+----------------+\n";
    }
}
