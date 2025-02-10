package bricker.brickStrategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 * A strategy that combines two collision behaviors, executing them sequentially
 * upon collision, and updates a brick counter.
 *
 * @author fanteo12
 */
public class DoubleBehaviorStrategy  implements DoubleBehaviorDecorator{

    private final CollisionStrategy behavior1; // The first collision behavior
    private final CollisionStrategy behavior2; // The second collision behavior
    private final Counter bricksCount; // Counter for tracking the number of bricks

    /**
     * Constructs a DoubleBehaviorStrategy with the specified brick count and collision behaviors.
     *
     * @param bricksCount The counter for tracking the number of bricks.
     * @param behavior1  The first collision behavior to be executed on collision.
     * @param behavior2  The second collision behavior to be executed on collision.
     */
    public DoubleBehaviorStrategy(Counter bricksCount, CollisionStrategy behavior1,
                                  CollisionStrategy behavior2) {
        this.behavior1 = behavior1;
        this.behavior2 = behavior2;
        this.bricksCount = bricksCount;
    }

    /**
     * Executes the combined collision behavior when a collision occurs between two game objects.
     * This method first invokes the onCollision method of the first behavior, then the onCollision method
     * of the second behavior. It also increments the brick count after the collision.
     *
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        // Execute the first behavior
        behavior1.onCollision(object1, object2);

        // Execute the second behavior
        behavior2.onCollision(object1, object2);

    }
}
