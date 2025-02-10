package bricker.brickStrategies;

import bricker.main.BrickerGameManager;
import bricker.utils.Constants;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * TurboStrategy is a specialized collision strategy that triggers a turbo mode
 * in the game upon a collision event. It extends the BasicCollisionStrategy and
 * adds custom behavior for enabling turbo mode.
 *
 * @author fanteo12
 */
public class TurboStrategy extends BasicCollisionStrategy {

    /**
     * Constructs a TurboStrategy instance.
     *
     * @param brickerGameManager Reference to the game's main manager.
     * @param bricksCount        Counter to track the number of bricks in the game.
     */
    public TurboStrategy(BrickerGameManager brickerGameManager, Counter bricksCount) {
        super(brickerGameManager, bricksCount);
    }

    /**
     * Handles collision events by first invoking the base class's collision behavior
     * and then enabling turbo mode in the game manager.
     *
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        // Invoke the basic collision behavior
        super.onCollision(object1, object2);

        // Enable turbo mode in the game manager
        if (object2.getTag().equals(Constants.BALL_TAG_NAME)) {
            gameManager.setTurboOn();
        }
    }
}
