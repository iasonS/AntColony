package environment.ant;

import creatures.AntEgg;
import environment.Food;
import io.micronaut.context.annotation.Prototype;
import java.util.List;
import lombok.AllArgsConstructor;
import utils.Coordinates;

@AllArgsConstructor
@Prototype
public class AntNest {
    private final Coordinates coordinates;
    private final List<AntEgg> eggsInNest;
    private final Food foodInNest;
}
