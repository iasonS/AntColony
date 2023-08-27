package creatures;

import io.micronaut.context.annotation.Prototype;
import lombok.AllArgsConstructor;

@Prototype
@AllArgsConstructor
public class AntEgg {

    private Class<?> antType;
    private int turnsIncubationRemaining;
}
