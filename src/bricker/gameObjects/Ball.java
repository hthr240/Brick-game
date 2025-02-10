package bricker.gameObjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static bricker.utils.Constants.*;

/**
 * Represents the ball in the Bricker game.
 * The ball moves in the game space, bounces off surfaces upon collision,
 * and plays a sound whenever a collision occurs.
 *
 * @author fanteo12
 */
public class Ball extends GameObject {

    private final Sound collisionSound; // Sound to play upon collision
    private int collisionCounter = 0;

    /**
     * Constructs a new Ball instance.
     *
     * @param topLeftCorner  Position of the ball, in window coordinates (pixels).
     *                       (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height of the ball in window coordinates.
     * @param renderable     The renderable representing the ball. Can be null, in which case
     *                       the ball will not be rendered.
     * @param collisionSound The sound to play whenever the ball collides with another object.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.setVelocity(new Vector2(BALL_SPEED,BALL_SPEED));
    }

    /**
     * Gets the current collision counter value.
     *
     * @return the current collision counter value.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

    /**
     * Called when the ball collides with another object.
     * Plays a collision sound, increments the collision counter, and flips the ball's velocity
     * based on the collision's normal vector.
     *
     * @param other     The GameObject with which the ball collided.
     * @param collision Collision data, including the normal vector of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        this.collisionCounter++;
        // Play the collision sound
        collisionSound.play();
        // Flip the velocity based on the collision's normal vector
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
    }
}
