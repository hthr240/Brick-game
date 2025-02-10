package bricker.brickStrategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * Implements a collision strategy that spawns a temporary paddle upon collision.
 * This strategy extends the BasicCollisionStrategy, adding functionality to
 * create a temporary paddle in response to collisions.
 *
 * @author fanteo12
 */
public class TempPaddleStrategy extends BasicCollisionStrategy {

    /**
     * Constructs a TempPaddleStrategy instance.
     *
     * @param brickerGameManager The BrickerGameManager instance managing game objects and logic.
     * @param bricksCount        A Counter tracking the number of bricks remaining in the game.
     */
    public TempPaddleStrategy(BrickerGameManager brickerGameManager, Counter bricksCount) {
        super(brickerGameManager, bricksCount);
    }

    /**
     * Handles the collision between two GameObjects. Overrides the method in the superclass
     * to include logic for creating a temporary paddle.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param object2 The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        super.onCollision(object1, object2);
        gameManager.createTempPaddle();
    }
}
