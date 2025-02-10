package bricker.gameObjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.utils.Constants.BALL_TAG_NAME;
import static bricker.utils.Constants.PUCK_TAG_NAME;

/**
 * Represents a paddle in the Bricker game.
 * The paddle is a movable game object controlled by the user through the keyboard.
 * It can move left and right within defined boundaries.
 *
 * @author fanteo12
 */
public class TempPaddle extends Paddle {

    private final Counter paddleCollisionCounter;

    /**
     * Constructs a new Paddle instance.
     *
     * @param topLeftCorner  Position of the paddle, in window coordinates (pixels).
     *                       (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height of the paddle in window coordinates.
     * @param renderable     The renderable representing the paddle. Can be null, in which case
     *                       the paddle will not be rendered.
     * @param inputListener  A listener for handling user keyboard input.
     * @param windowWidth    The width of the game window, used to determine movement boundaries.
     */
    public TempPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, float windowWidth,Counter tempPaddleCollisionCounter) {
        super(topLeftCorner,dimensions,renderable,inputListener,windowWidth);
        this.paddleCollisionCounter = tempPaddleCollisionCounter;
    }

    /**
     * Determines whether the paddle should collide with another GameObject.
     * This method ensures the paddle only interacts with objects tagged as "BALL" or "PUCK".
     *
     * @param other The other GameObject to check collision eligibility.
     * @return True if the paddle should collide with the object; otherwise, false.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) && (other.getTag().equals(BALL_TAG_NAME) ||
                other.getTag().equals(PUCK_TAG_NAME));
    }

    /**
     * Overrides the onCollisionEnter method from the superclass (Paddle).
     * Decrements the paddleCollisionCounter when a collision occurs.
     *
     * @param other     The GameObject involved in the collision.
     * @param collision The Collision object representing the collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        paddleCollisionCounter.decrement();
    }

    /**
     * Checks if the paddle's work has ended by verifying if the paddleCollisionCounter is zero.
     *
     * @return True if the paddle's work has ended; otherwise, false.
     */
    public boolean checkIfPaddleWorkEnd() {
        return paddleCollisionCounter.value() == 0;
    }
}
