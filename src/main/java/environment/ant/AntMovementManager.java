package environment.ant;

import creatures.Ant;
import environment.game.PositionManager;
import environment.pheromone.Pheromone;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.strtree.STRtree;
import utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class AntMovementManager {

    private static int ANT_VISIBILITY_RANGE = 5;

    private STRtree quadtree;

    @Inject
    private PositionManager positionManager;

    public AntMovementManager(){
        quadtree = new STRtree();
    }

    public void startMovement(AntColony colony) {
        ArrayList<Pheromone> pheromones = new ArrayList<>(positionManager.getCoordinatesPheromoneMap().get(colony.getUniqueIdentifier()).values());
        for (Pheromone pheromone : pheromones) {
            Envelope envelope = new Envelope(
                    pheromone.getCoordinates().getX(),
                    pheromone.getCoordinates().getX(),
                    pheromone.getCoordinates().getY(),
                    pheromone.getCoordinates().getY()
            );
            quadtree.insert(envelope, pheromone);
        }

    }

    public void influenceAntMovement(Ant ant) {
        Envelope searchEnvelope = new Envelope(
                ant.getCurrentCoordinates().getX() - ANT_VISIBILITY_RANGE,
                ant.getCurrentCoordinates().getX() + ANT_VISIBILITY_RANGE,
                ant.getCurrentCoordinates().getY() - ANT_VISIBILITY_RANGE,
                ant.getCurrentCoordinates().getY() + ANT_VISIBILITY_RANGE
        );

        @SuppressWarnings("unchecked")
        List<Pheromone> nearbyPheromones = quadtree.query(searchEnvelope);

        if(nearbyPheromones != null){
            Pheromone strongestPheromone = null;
            // Apply logic to influence ant's movement based on nearby pheromones
            for (Pheromone pheromone : nearbyPheromones) {
                if(strongestPheromone == null || pheromone.getStrength() > strongestPheromone.getStrength()){
                    strongestPheromone = pheromone;
                }
            }
            if(strongestPheromone == null){
                ant.moveRandom();
            } else {
                Coordinates newAntCoordinates = ant.move(strongestPheromone.getCoordinates(), positionManager.getBlockedCoordinates());
                positionManager.updateNewCoordinatesForAnt(ant, newAntCoordinates);
            }
        } else {
            ant.moveRandom();
        }
    }

}
