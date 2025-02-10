package bricker.gameObjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a paddle in the Bricker game.
 * The paddle is a movable game object controlled by the user through the keyboard.
 * It can move left and right within defined boundaries.
 *
 * @author fanteo12
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 35; // Speed at which the paddle moves
    private final float rightLimit; // Right boundary for paddle movement
    private final UserInputListener inputListener; // Listener for user input

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
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, float windowWidth) {
        super(topLeftCorner, dimensions, renderable);
        this.rightLimit = windowWidth;
        this.inputListener = inputListener;
    }

    /**
     * Updates the paddle's position based on user input and ensures it stays within bounds.
     *
     * @param deltaTime Time elapsed since the last update (in seconds).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Default movement direction (stationary)
        Vector2 movementDir = Vector2.ZERO;

        // Handle user input for left and right movement
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT.mult(MOVEMENT_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        }

        // Set paddle velocity based on calculated movement direction
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        // Ensure the paddle remains within the left boundary
        if (this.getTopLeftCorner().x() < 0) {
            this.setTopLeftCorner(new Vector2(0, this.getTopLeftCorner().y()));
        }

        // Ensure the paddle remains within the right boundary
        if (this.getTopLeftCorner().x() + this.getDimensions().x() > rightLimit) {
            this.setTopLeftCorner(new Vector2(rightLimit - this.getDimensions().x(), this.getTopLeftCorner().y()));
        }
    }
}
