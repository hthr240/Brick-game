package bricker.brickStrategies;

import danogl.GameObject;

/**
 * Defines the strategy for handling collisions between game objects.
 * This interface allows different collision behaviors to be implemented and used
 * for various game objects like bricks, paddles, and balls in the Bricker game.
 * Implementing this interface allows for customization of collision handling, such as
 * triggering events when two objects collide, playing sounds, or modifying object states.
 * The primary method, onCollision, is invoked whenever two game objects collide.
 *
 * @author fanteo12
 */
public interface CollisionStrategy {

    /**
     * This method is called when two game objects collide.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     * The exact behavior of this method depends on the specific implementation of the
     * CollisionStrategy interface. For example, it could handle bouncing,
     * sound effects, or state changes of the involved objects.
     */
    void onCollision(GameObject obj1, GameObject obj2);
}
