package bricker.gameObjects;

import bricker.brickStrategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A class representing a brick object in the game. The brick is a type of GameObject
 * that can be destroyed upon collision. It implements a collision strategy to determine the
 * behavior when it collides with other objects, such as the ball.
 * The brick will decrement a counter when it is destroyed, which typically tracks the number
 * of remaining bricks in the game.
 *
 * @author fanteo12
 */
public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;

    /**
     * Constructs a new Brick object.
     *
     * @param topLeftCorner Position of the brick in window coordinates (pixels).
     *                      (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height of the brick in window coordinates.
     * @param renderable    The renderable representation of the brick (e.g., image or shape).
     *                      Can be {@code null}, in which case the brick will not be rendered.
     * @param collisionStrategy The strategy to handle collisions when the brick is hit.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Called when a collision with another game object occurs.
     * If the brick has not already been destroyed, this method invokes the collision strategy
     * and decrements the brick count.
     *
     * @param other The other game object involved in the collision.
     * @param collision The collision data.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this, other);
    }
}
