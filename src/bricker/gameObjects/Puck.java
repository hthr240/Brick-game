package bricker.gameObjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Puck class represents a puck GameObject in the Bricker game.
 * It extends the Ball class and inherits the behavior of a bouncing ball with collisions.
 * Puck adds no additional behavior to the Ball class, but it serves as a distinct type of ball in the game.
 *
 * @author fanteo12
 */
public class Puck extends Ball {

    /**
     * Constructs a Puck object with the specified top-left corner, dimensions, renderable, and collision
     * sound.
     *
     * @param topLeftCorner  the Vector2 representing the top-left corner position of the puck.
     * @param dimensions     the Vector2 representing the dimensions of the puck.
     * @param renderable     the Renderable object for rendering the puck.
     * @param collisionSound the Sound object for playing collision sounds.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable,collisionSound);
    }
}
