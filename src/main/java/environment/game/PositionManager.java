package environment.game;

import creatures.Ant;
import environment.pheromone.Pheromone;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Data;
import utils.Coordinates;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Singleton
@Data
public class PositionManager {

    private final Map<Integer, Map<Coordinates, Pheromone>> coordinatesPheromoneMap;
    private final Set<Coordinates> blockedCoordinates;

    public boolean updateNewCoordinatesForAnt(Ant ant, Coordinates coordinates){

    }

}
