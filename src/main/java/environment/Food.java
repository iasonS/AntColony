package environment;

import io.micronaut.context.annotation.Prototype;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Prototype
@AllArgsConstructor
public class Food {
    private int foodValue = 35;

    public boolean updateFoodValue(int foodValue) {
        this.foodValue += foodValue;
        return foodValue > 0;
    }
}
