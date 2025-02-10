package bricker.brickStrategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * Implements a collision strategy that creates pucks upon a collision.
 * This strategy extends the BasicCollisionStrategy and enhances its behavior by spawning pucks
 * at the location of the collision.
 *
 * @author fanteo12
 */
public class PuckStrategy extends BasicCollisionStrategy {

    /**
     * Constructs a {@code PuckStrategy} instance.
     *
     * @param brickerGameManager The rickerGameManager instance managing game objects and logic.
     * @param bricksCount        A Counter tracking the number of bricks remaining in the game.
     */
    public PuckStrategy(BrickerGameManager brickerGameManager, Counter bricksCount) {
        super(brickerGameManager, bricksCount);
    }

    /**
     * Handles the collision between two GameObjects. Overrides the method in the superclass
     * to include logic for creating pucks.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param object2 The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        gameManager.createPucks(object1.getCenter());
    }
}
