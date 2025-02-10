package bricker.utils;

import danogl.util.Vector2;

/**
 * A utility class that defines constants used throughout the Bricker game.
 * This includes resource paths, strategy identifiers, gameplay constants, and object tags.
 * The class provides centralized management of these constants to ensure consistency
 * and maintainability across the game codebase.
 *
 * @author fanteo12
 */
public class Constants {

    // RESOURCE PATHS //

    /**
     * The title of the game window.
     */
    public static final String WINDOW_TITLE = "Bricker Game";

    /**
     * File path for the brick image asset.
     */
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";

    /**
     * File path for the paddle image asset.
     */
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";

    /**
     * File path for the ball image asset.
     */
    public static final String BALL_IMAGE_PATH = "assets/ball.png";

    /**
     * File path for the heart image asset.
     */
    public static final String HEART_IMAGE_PATH = "assets/heart.png";

    /**
     * File path for the sound effect played during collisions.
     */
    public static final String BLOP_SOUND_PATH = "assets/blop.wav";

    /**
     * File path for the mock puck image asset.
     */
    public static final String PUCK_PATH = "assets/mockBall.png";

    /**
     * File path for the background image asset.
     */
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * File path for the turbo ball image asset.
     */
    public static final String TURBO_BALL_PATH = "assets/redball.png";

    // OBJECT TAGS //

    /**
     * The tag name assigned to the main ball in the game.
     */
    public static final String BALL_TAG_NAME = "mainBall";

    /**
     * The tag name assigned to the main paddle in the game.
     */
    public static final String PADDLE_TAG_NAME = "mainPaddle";

    /**
     * The tag name assigned to puck objects in the game.
     */
    public static final String PUCK_TAG_NAME = "puck";

    /**
     * The tag name assigned to extra heart objects in the game.
     */
    public static final String EXTRA_HEART_TAG = "extraHeart";


// STRATEGY IDENTIFIERS //

    /**
     * Identifier for the puck strategy.
     */
    public static final int PUCK_STRATEGY = 0;

    /**
     * Identifier for the temporary paddle strategy.
     */
    public static final int TEMP_PADDLE_STRATEGY = 1;

    /**
     * Identifier for the turbo ball strategy.
     */
    public static final int TURBO_STRATEGY = 2;

    /**
     * Identifier for the extra life strategy.
     */
    public static final int EXTRA_LIFE_STRATEGY = 3;

    /**
     * Identifier for the double behavior strategy.
     */
    public static final int DOUBLE_BEHAVIOR_STRATEGY = 4;

    /**
     * Identifier for the basic collision strategy.
     */
    public static final int BASIC_COLLISION = 5;

    /**
     * Total number of brick-related strategies available.
     */
    public static final int BRICKS_STRATEGIES_NUM = 5;

    /**
     * The initial counter value for brick behavior strategy in the game.
     */
    public static final int STRATEGY_START_COUNTER = 0;

    // CONSTANTS THAT ARE USED IN MULTIPLE CLASSES //

    /**
     * The number of strikes a player has at the start of the game.
     */
    public static final int STRIKES = 3;

    /**
     * The dimensions of the heart objects displayed in the game.
     */
    public static final Vector2 HEART_DIMENSIONS = new Vector2(15, 15);

    /**
     * The speed multiplier for the turbo ball strategy, increasing ball speed by 40%.
     */
    public static final float TURBO_FACTOR = 1.4f;

    /**
     * The speed of the main ball.
     */
    public static final float BALL_SPEED = 250;
}