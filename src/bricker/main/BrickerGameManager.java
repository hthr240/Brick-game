package bricker.main;

import bricker.brickStrategies.CollisionStrategy;
import bricker.brickStrategies.StrategyFactory;
import bricker.gameObjects.*;
import bricker.utils.Constants;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * Manages the Bricker game by initializing the game environment, objects, and behavior.
 * Extends the GameManager class from the danogl framework and controls game flow, object creation,
 * and interactions within the game.
 * This class handles the creation of various game objects like the paddle, ball, bricks, and extra lives.
 * It also manages user input, game state updates, and triggers actions such as checking for game end conditions
 * or ball collisions.
 *
 * @author fanteo12
 */
public class BrickerGameManager extends GameManager {

    // Window
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(800, 600);
    // Borders
    private static final float BORDER_WIDTH = 15;
    private static final float BORDER_PADDING = BORDER_WIDTH * 1.5f;
    // Ball
    private static final Vector2 BALL_SIZE = new Vector2(20, 20);
    private static final int TURBO_BALL_STRIKES = 6;
    // Pucks
    private static final Vector2 PUCK_SIZE = new Vector2(BALL_SIZE.x()*0.75f, BALL_SIZE.y()*0.75f);
    private static final int NUM_OF_PUCKS = 2;
    // Paddle & Temp Paddle
    private static final Vector2 PADDLE_SIZE = new Vector2(100, 15);
    private static final Vector2 PADDLE_POSITION = new Vector2(WINDOW_DIMENSIONS.x() / 2, WINDOW_DIMENSIONS.y() - BORDER_PADDING*2);
    private static final Vector2 TEMP_PADDLE_POS = new Vector2(WINDOW_DIMENSIONS.x()/2,WINDOW_DIMENSIONS.y()/2);
    private static boolean tempPaddleOn = false;
    private static final int TEMP_PADDLE_COLLISION_COUNT = 4;
    // Bricks
    private static final int BRICK_HEIGHT = 15;
    private static final int DEFAULT_BRICKS_ROW = 7;
    private static final int DEFAULT_BRICKS_IN_ROW = 8;
    private static final int BRICK_PADDING = 5;
    // Heart & Strikes
    private static final int HEART_PADDING = 5;
    private static final int HEART_SPEED = 100;
    private static final int MAX_STRIKES = 4;
    // End game
    private static final String LOSE_PROMPT = "You lose!";
    private static final String PLAY_AGAIN_PROMPT = " Play again?";
    private static final String WIN_PROMPT = "You Win!";

    // Game Manager methods
    private WindowController windowController; // Manages the game window
    private UserInputListener inputListener; // Handles user input
    private ImageReader imageReader; // Reads images for rendering
    private SoundReader soundReader; // Reads sound files

    // Objects
    private Ball ball;
    private LifePanel lifePanel;
    private TempPaddle tempPaddle;
    private Puck[] pucks;

    // Counters
    private Counter strikes;
    private Counter bricksCount;

    private final Random random = new Random();
    private final int numOfBricksRows;
    private final int numOfBricksCols;
    private boolean isTurbo = false;
    private int collisionWithBrickNumber;
    private ImageRenderable ballImage;
    private ImageRenderable turboBallImage;

    /**
     * Constructs a new BrickerGameManager instance with default brick layout.
     *
     * @param windowTitle      Title of the game window.
     * @param windowDimensions Dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.numOfBricksRows = DEFAULT_BRICKS_ROW;
        this.numOfBricksCols = DEFAULT_BRICKS_IN_ROW;
    }

    /**
     * Constructs a new BrickerGameManager instance with specified brick layout.
     *
     * @param windowTitle      Title of the game window.
     * @param windowDimensions Dimensions of the game window.
     * @param numOfBricksRows  Number of rows of bricks.
     * @param numOfBricksCols  Number of bricks in each row.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numOfBricksRows,
                              int numOfBricksCols) {
        super(windowTitle, windowDimensions);
        this.numOfBricksRows = numOfBricksRows;
        this.numOfBricksCols = numOfBricksCols;
    }

    /**
     * Initializes the game environment, including background, walls, paddle, ball, and bricks.
     *
     * @param imageReader      The image reader for loading assets.
     * @param soundReader      The sound reader for loading sound effects.
     * @param inputListener    The input listener for user controls.
     * @param windowController The window controller for managing window properties.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        this.windowController = windowController;
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.strikes = new Counter(Constants.STRIKES);
        this.bricksCount = new Counter(numOfBricksCols * numOfBricksRows);
        this.pucks = new Puck[NUM_OF_PUCKS];
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        initBackground();
        initWalls();
        initBall();
        initPaddle();
        initBricks();
        initLifePanel();
    }

    /**
     * Updates the game state, including checking if the ball has fallen off the screen and whether the game has ended.
     *
     * @param deltaTime Time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // handle game end
        checkForGameEnd();

        // handle ball fall
        handleBallFall();

        // handle temp paddle
        handleTempPaddle();

        // handle pucks
        checkIfPucksFall(pucks);

        // handle turbo ball
        if (isTurbo && ball.getCollisionCounter() >
            collisionWithBrickNumber+TURBO_BALL_STRIKES) {
            setTurboOff();
        }

        // handle falling heart
        checkIfHeartFall();
        checkIfHeartCatch();
    }

    /**
     * Turns on turbo mode for the ball.
     */
    public void setTurboOn(){
        if (!isTurbo){
            collisionWithBrickNumber = ball.getCollisionCounter();
            ball.setVelocity(ball.getVelocity().mult(Constants.TURBO_FACTOR));
            ball.renderer().setRenderable(turboBallImage);
            isTurbo = true;
        }
    }

    /**
     * Turns off turbo mode for the ball.
     */
    public void setTurboOff(){
        isTurbo = false;
        ball.setVelocity(ball.getVelocity().mult(1/Constants.TURBO_FACTOR));
        ball.renderer().setRenderable(ballImage);
    }

    /**
     * Checks if the heart object has fallen off the screen and removes it if it has.
     */
    private void checkIfHeartFall() {
        for (GameObject object : gameObjects()) {
            if (object.getTag().equals(Constants.EXTRA_HEART_TAG) && object.getCenter().y() > WINDOW_DIMENSIONS.y()) {
                gameObjects().removeGameObject(object);
            }
        }
    }

    /**
     * Checks if the heart object has been caught by the player and updates the number of strikes.
     * If the heart is caught, a new heart object is created and added to the life panel.
     */
    private void checkIfHeartCatch() {
        for (GameObject gameObject: gameObjects()) {
            if (gameObject.getTag().equals(Constants.EXTRA_HEART_TAG) &&
                    strikes.value() < MAX_STRIKES &&
                    ((Heart) gameObject).getIsHeartTaken()) {

                strikes.increment();
                GameObject newHeart = new Heart(gameObject.getTopLeftCorner(), Constants.HEART_DIMENSIONS,
                        gameObject.renderer().getRenderable());
                lifePanel.updateLives(strikes, newHeart);
                gameObjects().removeGameObject(gameObject);
                gameObjects().addGameObject(lifePanel.getLastElement(), Layer.UI);
            }
        }
    }

    /**
     * Creates an extra life in the form of a heart object and adds it to the game objects.
     *
     * @param extraHeartPosition The position of the extra heart.
     */
    public void createExtraLife(Vector2 extraHeartPosition) {
        Renderable heartImage = imageReader.readImage(Constants.HEART_IMAGE_PATH, true);
        Heart extraHeart = new Heart(extraHeartPosition, Constants.HEART_DIMENSIONS, heartImage);
        extraHeart.setTag(Constants.EXTRA_HEART_TAG);
        extraHeart.setVelocity(Vector2.DOWN.mult(HEART_SPEED));
        gameObjects().addGameObject(extraHeart);
    }

    /**
     * Manages the lifecycle of the temporary paddle.
     * Removes the paddle and resets its flag if its work is complete.
     */
    private void handleTempPaddle() {

        if (tempPaddle != null && tempPaddle.checkIfPaddleWorkEnd()){
            tempPaddleOn = false;
            gameObjects().removeGameObject(tempPaddle);
        }
    }

    /**
     * Creates an extra paddle if the maximum limit is not reached.
     */
    public void createTempPaddle() {
        if (!tempPaddleOn) {
            Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE_PATH, true);
            tempPaddle = new TempPaddle(TEMP_PADDLE_POS, PADDLE_SIZE, paddleImage,
                    inputListener, WINDOW_DIMENSIONS.x(),new Counter(TEMP_PADDLE_COLLISION_COUNT));
            gameObjects().addGameObject(tempPaddle);
            tempPaddleOn = true;
        }
    }

    /**
     * check if puck ball fall and remove it
     * @param pucks an array of puck objects
     */
    private void checkIfPucksFall(GameObject[] pucks) {
        for (GameObject object : pucks) {
            if (object != null && object.getCenter().y() > WINDOW_DIMENSIONS.y()) {
                gameObjects().removeGameObject(object);
            }
        }
    }

    /**
     * Creates multiple pucks at the specified location.
     *
     *
     * @param location The location where the pucks should be created.
     */
    public void createPucks(Vector2 location) {
        Renderable puckImage = imageReader.readImage(Constants.PUCK_PATH, true);
        Sound collisionSound = soundReader.readSound(Constants.BLOP_SOUND_PATH);

        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            pucks[i] = new Puck(location, PUCK_SIZE, puckImage, collisionSound);
            pucks[i].setTag(Constants.PUCK_TAG_NAME);
            double angle = random.nextDouble() * Math.PI;
            float velocityX = (float)Math.cos(angle) * Constants.BALL_SPEED;
            float velocityY = (float)Math.sin(angle) * Constants.BALL_SPEED;
            pucks[i].setVelocity(new Vector2(velocityX,velocityY));
            gameObjects().addGameObject(pucks[i]);
        }
    }

    /**
     * Checks for win or loss conditions and handles the end of the game.
     */
    private void checkForGameEnd(){
        // get string for result prompt
        String prompt = "";
        // check win condition
        if (bricksCount.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt = WIN_PROMPT;
        }
        // check lose condition
        if (ball.getCenter().y() > WINDOW_DIMENSIONS.y() && strikes.value() == 1){

            prompt = LOSE_PROMPT;
        }
        // handle result prompt
        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN_PROMPT;
            // handle end game
            if (windowController.openYesNoDialog((prompt))) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * Checks if the ball has fallen off the screen and updates the player's lives.
     */
    private void handleBallFall() {
        // check if ball out of bound
        if (ball.getCenter().y() > WINDOW_DIMENSIONS.y() && strikes.value() > 0) {
            //update life panel
            strikes.decrement();// update strikes
            GameObject heartToRemove = lifePanel.getLastElement();
            gameObjects().removeGameObject(heartToRemove,Layer.UI);
            lifePanel.updateLives(strikes,null);// update lifePanel
            // reset ball
            ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
            setBallVelocity();
        }
    }

    /**
     * Initializes the life panel which displays the number of lives remaining, both numerically and with graphical hearts.
     */
    private void initLifePanel() {
        // create lifePanel
        Renderable heartImage = imageReader.readImage(Constants.HEART_IMAGE_PATH,true);
        Vector2 lifePanelPosition = new Vector2(HEART_PADDING*2,WINDOW_DIMENSIONS.y() - BORDER_PADDING);
        lifePanel = new LifePanel(strikes,lifePanelPosition,heartImage);
        locateLifePanel();
    }

    /**
     * put life panel on board
     */
    private void locateLifePanel(){
        // add lifePanel objects to gameObjects
        List<GameObject> staticLives = lifePanel.getStaticLives();
        for (GameObject staticLive: staticLives){
            gameObjects().addGameObject(staticLive,Layer.UI);
        }
    }

    /**
     * Initializes the brick layout in the game. Bricks are placed in a grid pattern
     * with padding between them. Each brick uses a collision strategy for handling interactions.
     */
    private void initBricks() {
        Renderable brickImage = imageReader.readImage(Constants.BRICK_IMAGE_PATH, true);
        float brickWidth = (WINDOW_DIMENSIONS.x() - BORDER_WIDTH * 2 - (BRICK_PADDING * numOfBricksCols - 1)) / numOfBricksCols;

        for (int i = 0; i < numOfBricksRows; i++) {
            for (int j = 0; j < numOfBricksCols; j++) {
                Vector2 brickPos = getBrickPosition(i,j,brickWidth);
                CollisionStrategy collisionStrategy = getCollisionStrategy();
                GameObject brick = new Brick(brickPos, new Vector2(brickWidth, BRICK_HEIGHT), brickImage, collisionStrategy);
                gameObjects().addGameObject(brick,Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     *  get brick position
     * @param i row number
     * @param j col number
     * @param brickWidth the width of the brick
     * @return Vector2 = brickPos
     */
    private Vector2 getBrickPosition(int i, int j, float brickWidth) {
        float brickX = BORDER_WIDTH + j * (brickWidth + BRICK_PADDING);
        float brickY = BORDER_WIDTH + i * (BRICK_HEIGHT + BRICK_PADDING);
        return new Vector2(brickX, brickY);
    }

    /**
     * get a CollisionStrategy for bricks
     * @return CollisionStrategy instance
     */
    public CollisionStrategy getCollisionStrategy() {
        StrategyFactory strategyFactory = new StrategyFactory(this, bricksCount);
        if (random.nextBoolean()){
            return strategyFactory.buildStrategy(Constants.BASIC_COLLISION,Constants.STRATEGY_START_COUNTER);
        } else {
            int index = random.nextInt(Constants.BRICKS_STRATEGIES_NUM);
            return strategyFactory.buildStrategy(index,Constants.STRATEGY_START_COUNTER);
        }
    }

    /**
     * Selects a normal behavior strategy for a brick using the StrategyFactory.
     *
     * @param brickCount the Counter representing the number of remaining bricks.
     * @return the selected CollisionStrategy for the brick.
     */
    public CollisionStrategy selectNormalStrategyBehavior(Counter brickCount) {
        StrategyFactory strategyFactory = new StrategyFactory(this, brickCount);
        int index = random.nextInt(Constants.DOUBLE_BEHAVIOR_STRATEGY);
        return strategyFactory.buildStrategy(index, Constants.STRATEGY_START_COUNTER);
    }

    /**
     *
     * @param object object to ramove
     * @param layer object layer
     * @return true if removed object
     */
    public boolean removeGameObject(GameObject object, int layer) {
        return gameObjects().removeGameObject(object,layer);
    }

    /**
     * Initializes the paddle at the bottom of the game window.
     * The paddle responds to user input for left and right movement.
     */
    private void initPaddle() {
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE_PATH, true);
        GameObject paddle = new Paddle(PADDLE_POSITION, PADDLE_SIZE, paddleImage, inputListener, WINDOW_DIMENSIONS.x());
        paddle.setTag(Constants.PADDLE_TAG_NAME);
        gameObjects().addGameObject(paddle);
    }

    /**
     * Initializes the ball at the center of the game window with a random velocity.
     * The ball bounces off surfaces and triggers collisions with other game objects.
     */
    private void initBall() {
        // get renderables
        ballImage = imageReader.readImage(Constants.BALL_IMAGE_PATH, true);
        turboBallImage = imageReader.readImage(Constants.TURBO_BALL_PATH,true);
        // get collision sound
        Sound collisionSound = soundReader.readSound(Constants.BLOP_SOUND_PATH);
        // create ball
        ball = new Ball(Vector2.ZERO, BALL_SIZE, ballImage, collisionSound);
        ball.setTag(Constants.BALL_TAG_NAME);
        // locate ball on board
        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
        setBallVelocity();
        gameObjects().addGameObject(ball);
    }

    /**
     * sets a ball velocity on the game board.
     */
    private void setBallVelocity() {
        float ballVelY = Constants.BALL_SPEED;
        float ballVelX = Constants.BALL_SPEED;
        if (random.nextBoolean()) {
            ballVelX *= -1;
        }
        if (random.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX,ballVelY));
    }

    /**
     * Creates the boundary walls to confine the ball within the game window.
     * Walls are placed at the left, right, and top edges of the window.
     */
    private void initWalls() {
        GameObject leftWall = new GameObject(Vector2.ZERO,
                                new Vector2(BORDER_WIDTH, WINDOW_DIMENSIONS.y()), null);
        gameObjects().addGameObject(leftWall);

        GameObject rightWall = new GameObject(new Vector2(WINDOW_DIMENSIONS.x() - BORDER_WIDTH, 0),
                                new Vector2(BORDER_WIDTH, WINDOW_DIMENSIONS.y()), null);
        gameObjects().addGameObject(rightWall);

        GameObject upperWall = new GameObject(Vector2.ZERO,
                                new Vector2(WINDOW_DIMENSIONS.x(), BORDER_WIDTH), null);
        gameObjects().addGameObject(upperWall);
    }

    /**
     * Initializes the background image of the game.
     * The background spans the entire window.
     */
    private void initBackground() {
        Renderable bgImage = imageReader.readImage(Constants.BACKGROUND_IMAGE_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, WINDOW_DIMENSIONS, bgImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * The entry point for the Bricker game. Allows optional customization of brick layout via command-line arguments.
     *
     * @param args Optional command-line arguments: [number of rows, number of columns].
     */
    public static void main(String[] args) {
        BrickerGameManager brickerGameManager;
        if (args.length == 2) {
            brickerGameManager = new BrickerGameManager(Constants.WINDOW_TITLE,
                                WINDOW_DIMENSIONS, Integer.parseInt(args[1]), Integer.parseInt(args[0]));
        } else {
            brickerGameManager = new BrickerGameManager(Constants.WINDOW_TITLE, WINDOW_DIMENSIONS);
        }
        brickerGameManager.run();
    }

}
