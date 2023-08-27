package environment.game;

import static environment.game.GameBoardParams.COLONY_COORD_BOUNDARY;

import creatures.Ant;
import creatures.AntFactory;
import environment.Food;
import environment.ant.AntColony;
import environment.ant.AntMovementManager;
import environment.pheromone.Pheromone;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import utils.Coordinates;

@Slf4j
@Singleton
public class GameBoard {

    private int coloniesCreated = 0;
    private boolean running = true;

    private final Random random = new Random();

    private final Map<Coordinates, AntColony> coordinatesAntColonyMap;
    // Colony Identifier, Coordinates, Pheromone
    private final Map<Coordinates, Food> foodMap;
    private final List<AntColony> antColonies;

    private final AntFactory antFactory;
    private final AntMovementManager antMovementManager;
    private final PositionManager positionManager;

    @Inject
    public GameBoard(AntFactory antFactory,
                     AntMovementManager antMovementManager,
                     PositionManager positionManager) {
        this.coordinatesAntColonyMap = new HashMap<>();
        this.foodMap = new HashMap<>();
        this.antColonies = new ArrayList<>();
        this.antFactory = antFactory;
        this.antMovementManager = antMovementManager;
        this.positionManager = positionManager;
    }

    @EventListener
    void onStartup(ServerStartupEvent event) {
        startGame();
    }

    private void startGame() {
        generateNewColonies();
        beginFrameLoop();
    }

    private void generateNewColonies() {

        Coordinates coordinates1 =
                new Coordinates(
                        random.nextInt(GameBoardParams.getXMin() + COLONY_COORD_BOUNDARY) + 1,
                        random.nextInt(GameBoardParams.getYMin() + COLONY_COORD_BOUNDARY) + 1);
        AntColony antColony1 = new AntColony(getColoniesUniqIdentifier(), antFactory, coordinates1);

        Coordinates coordinates2 =
                new Coordinates(
                        GameBoardParams.getXMax()
                                - random.nextInt(GameBoardParams.getXMax() - COLONY_COORD_BOUNDARY)
                                + 1,
                        GameBoardParams.getYMax()
                                - random.nextInt(GameBoardParams.getYMax() - COLONY_COORD_BOUNDARY)
                                + 1);
        AntColony antColony2 = new AntColony(getColoniesUniqIdentifier(), antFactory, coordinates2);

        antColonies.add(antColony1);
        antColonies.add(antColony2);
    }

    private void beginFrameLoop() {
        long lastTime = System.nanoTime();
        double nsPerUpdate = 1000000000.0 / 5.0; // Target time per frame for 60 FPS

        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            while (delta >= 1.0) {

                updateGame();
                delta -= 1.0;
            }

            // renderGame();

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                log.info("FPS: " + frames);
                frames = 0;
            }
        }
    }

    private void updateGame() {
        spawnOrUpdateFood();
        moveAnts();

        for (Map.Entry<Coordinates, AntColony> entry : coordinatesAntColonyMap.entrySet()) {
            Coordinates coordinates = entry.getKey();
            AntColony antColony = entry.getValue();

            if (log.isDebugEnabled()) {
                log.debug("\n{}\n{}", coordinates.toString(), antColony.toString());
            }
        }
        log.info(
                "Current total food: {}",
                foodMap.values().stream().mapToInt(Food::getFoodValue).sum());
        logFoodMapTable(foodMap);
    }

    private void moveAnts(){
        for(AntColony colony : antColonies){
            List<Ant> antsToMove = colony.provideAntsToMove();
            antMovementManager.startMovement(colony);
            antsToMove.forEach(antMovementManager::influenceAntMovement);
        }
    }

    private void logFoodMapTable(Map<Coordinates, Food> foodMap) {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("Food Location Table:\n");
        tableBuilder.append("+------------------+------------------+------------------+\n");
        tableBuilder.append("| X                | Y                | FOOD             |\n");
        tableBuilder.append("+------------------+------------------+------------------+\n");
        for (Map.Entry<Coordinates, Food> entry : foodMap.entrySet()) {
            Coordinates coordinates = entry.getKey();
            String row =
                    String.format(
                            "| %-16d | %-16d | %-16d |\n",
                            coordinates.getX(),
                            coordinates.getY(),
                            entry.getValue().getFoodValue());
            tableBuilder.append(row);
            tableBuilder.append("+------------------+------------------+------------------+\n");
        }

        log.info(tableBuilder.toString());
    }

    private void spawnOrUpdateFood() {
        for (Map.Entry<Coordinates, Food> entry : foodMap.entrySet()) {
            float foodUpdateChance = random.nextFloat();
            if (foodUpdateChance <= 0.35) {
                entry.getValue().updateFoodValue((int) (foodUpdateChance * 100) % 35);
            }
        }

        float createFoodChance = random.nextFloat();
        if (createFoodChance <= 0.1) {
            Coordinates newCoordinates =
                    Coordinates.generateNewRandomCoordinatesWithExclusions(foodMap.keySet());
            foodMap.put(newCoordinates, new Food(35));
        }

        if (foodMap.size() > 3) {
            float chanceOfDecay = random.nextFloat();
            if (chanceOfDecay <= 0.5) {
                int randomIndex = random.nextInt(foodMap.size());
                Object[] array = foodMap.keySet().toArray();
                Coordinates coordinatesOfFoodToDecay = (Coordinates) array[randomIndex];
                boolean hasFood =
                        foodMap.get(coordinatesOfFoodToDecay)
                                .updateFoodValue(((int) (chanceOfDecay * 100) % 50) * -1);
                if (!hasFood) {
                    foodMap.remove(coordinatesOfFoodToDecay);
                }
            }
        }
    }

    private void stopGame() {
        running = false;
    }

    private int getColoniesUniqIdentifier() {
        return ++coloniesCreated;
    }
}
