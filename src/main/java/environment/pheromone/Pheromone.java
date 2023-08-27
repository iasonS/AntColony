package environment.pheromone;

import io.micronaut.context.annotation.Prototype;
import lombok.AllArgsConstructor;
import lombok.Data;
import utils.Coordinates;

@Prototype
@AllArgsConstructor
@Data
public class Pheromone {
    private Coordinates coordinates;
    private int strength;
    private PheromoneType type;
}
