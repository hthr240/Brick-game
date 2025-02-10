package bricker.brickStrategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * A basic implementation of the {CollisionStrategy} interface that handles collisions
 * between game objects in a simple manner.
 * This strategy prints a message indicating a collision with a brick and removes the first
 * object from the game world (usually the brick) upon collision.
 * It is typically used for handling basic brick collision behavior, such as removing a brick
 * when it is hit by a ball.
 *
 * @author fanteo12
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    protected final BrickerGameManager gameManager;
    private final Counter bricksCount;
    /**
     * Constructs a BasicCollisionStrategy instance.
     *
     * @param brickerGameManager The collection of game objects to remove the collided object from.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager, Counter bricksCount) {
        this.gameManager = brickerGameManager;
        this.bricksCount = bricksCount;
    }

    /**
     * Handles the collision between two game objects. In this basic implementation, it prints
     * a message indicating that a collision has occurred and removes the first object (obj1)
     * from the game object collection.
     * @param object2 The first game object involved in the collision (typically a brick).
     * @param object1 The second game object involved in the collision (typically the ball).
     * The default behavior removes the object obj1 (usually a brick) from the game world.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (gameManager.removeGameObject(object1, Layer.STATIC_OBJECTS)) {
            bricksCount.decrement();
        }
    }
}
