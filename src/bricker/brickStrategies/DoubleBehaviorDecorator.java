package bricker.brickStrategies;

/**
 * Interface representing a decorator for implementing double behavior in collision strategies for bricks.
 * This interface extends the CollisionStrategy interface, allowing classes that implement it to act as
 * decorators that provide additional behavior to existing collision strategies for bricks.
 * Double behavior decorators modify the collision behavior of bricks in the game, typically by applying
 * special effects or interactions when collisions occur.
 * Classes that implement this interface are expected to provide concrete implementations for the methods
 * defined in the CollisionStrategy interface.
 *
 * @author fanteo12
 */
public interface DoubleBehaviorDecorator extends CollisionStrategy{
    // No additional methods are declared in this interface,
    // as it extends the CollisionStrategy interface and inherits its method
}
