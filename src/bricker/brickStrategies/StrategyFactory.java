package bricker.brickStrategies;

import bricker.main.BrickerGameManager;
import danogl.util.Counter;
import bricker.utils.Constants;

import java.util.Random;

/**
 * Factory class for creating various collision strategies used in the Bricker game.
 * It encapsulates the logic for selecting and constructing specific strategies
 * based on input parameters and game context.
 *
 * @author fanteo12
 */
public class StrategyFactory {

    private final BrickerGameManager gameManager; // Reference to the game manager
    private final Counter bricksCount; // Counter for tracking the number of bricks
    private static final int MAX_DOUBLE_BEHAVIOR_COUNT = 3; // Maximum allowed nested double behavior strategies

    /**
     * Constructs a StrategyFactory instance.
     *
     * @param brickerGameManager Reference to the game's main manager.
     * @param bricksCount        Counter to track the number of bricks in the game.
     */
    public StrategyFactory(BrickerGameManager brickerGameManager, Counter bricksCount) {
        this.gameManager = brickerGameManager;
        this.bricksCount = bricksCount;
    }

    /**
     * Builds a specific collision strategy based on the given index.
     *
     * @param index         The index representing the desired collision strategy type.
     * @param countStrategy The current count of double behavior strategies created.
     * @return A CollisionStrategy instance based on the specified index.
     */
    public CollisionStrategy buildStrategy(int index, int countStrategy) {
        switch (index) {
            case Constants.PUCK_STRATEGY:
                return new PuckStrategy(gameManager, bricksCount);
            case Constants.TEMP_PADDLE_STRATEGY:
                return new TempPaddleStrategy(gameManager, bricksCount);
            case Constants.TURBO_STRATEGY:
                return new TurboStrategy(gameManager, bricksCount);
            case Constants.EXTRA_LIFE_STRATEGY:
                return new ExtraHeartStrategy(gameManager, bricksCount);
            case Constants.DOUBLE_BEHAVIOR_STRATEGY:
                CollisionStrategy behavior1 = gameManager.selectNormalStrategyBehavior(bricksCount);
                countStrategy++;
                CollisionStrategy behavior2 = doubleBehaviourStrategy(countStrategy);
                return new DoubleBehaviorStrategy(bricksCount, behavior1, behavior2);
            default:
                return new BasicCollisionStrategy(gameManager, bricksCount);
        }
    }

    /**
     * Creates and returns a double behavior strategy. This method randomly selects
     * a collision strategy and combines it with another strategy to form a nested
     * double behavior strategy.
     *
     * @param countStrategy The current count of double behavior strategies created.
     * @return A CollisionStrategy instance representing a double behavior strategy.
     */
    public CollisionStrategy doubleBehaviourStrategy(int countStrategy) {
        Random rand = new Random();
        int index = rand.nextInt(Constants.DOUBLE_BEHAVIOR_STRATEGY + 1);

        // If the randomly selected index is not for a double behavior, create a standard strategy
        if (index != Constants.DOUBLE_BEHAVIOR_STRATEGY) {
            return buildStrategy(index, countStrategy + 1);
        }

        // If within the max count of nested double behaviors, create another double behavior
        if (countStrategy < MAX_DOUBLE_BEHAVIOR_COUNT) {
            return buildStrategy(index, countStrategy + 1);
        }

        // Fallback to a normal strategy if maximum nesting level is reached
        return gameManager.selectNormalStrategyBehavior(bricksCount);
    }
}
