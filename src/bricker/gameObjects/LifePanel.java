package bricker.gameObjects;

import bricker.utils.Constants;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static bricker.utils.Constants.HEART_DIMENSIONS;

/**
 * Represents the life panel in the Bricker game, displaying both a graphical and numeric representation of the player's remaining lives.
 * The life panel dynamically updates when the player loses or gains lives, ensuring both the hearts and numeric display reflect the
 * current state.
 *
 * @author fanteo12
 */
public class LifePanel extends GameObject {

    /**
     * The total number of objects in the life panel (1 numeric display + graphical hearts).
     */
    private static final int LIFE_PANEL_OBJECTS_NUM = Constants.STRIKES + 1;

    /**
     * Padding between graphical heart icons in pixels.
     */
    private static final int HEART_PADDING = 5;

    // Fields
    /**
     * List of GameObjects representing the numeric and graphical life indicators.
     */
    private final List<GameObject> staticLives;

    /**
     * Numeric display for the remaining lives.
     */
    private final TextRenderable numericRenderable;

    /**
     * Constructs a new LifePanel object.
     *
     * @param strikes       Counter tracking the remaining lives.
     * @param topLeftCorner The top-left position for the life panel.
     * @param heartImage    Renderable image for the graphical heart icons.
     */
    public LifePanel(Counter strikes, Vector2 topLeftCorner, Renderable heartImage) {
        super(topLeftCorner, HEART_DIMENSIONS, heartImage);

        this.staticLives = new ArrayList<>(LIFE_PANEL_OBJECTS_NUM);
        this.numericRenderable = new TextRenderable(Integer.toString(strikes.value()));
        this.numericRenderable.setColor(Color.GREEN);

        // Add numeric display to the life panel
        staticLives.add(new Heart(topLeftCorner, HEART_DIMENSIONS, numericRenderable));

        // Add graphical hearts
        for (int i = 1; i < LIFE_PANEL_OBJECTS_NUM; i++) {
            Vector2 heartPosition = topLeftCorner.add(new Vector2(
                    i * (HEART_DIMENSIONS.x() + HEART_PADDING), 0));
            staticLives.add(new Heart(heartPosition, HEART_DIMENSIONS, heartImage));
        }
    }

    /**
     * Retrieves the list of GameObjects in the life panel, which includes both numeric and graphical lives.
     *
     * @return A list of GameObjects representing the life indicators.
     */
    public List<GameObject> getStaticLives() {
        return this.staticLives;
    }

    /**
     * Retrieves the last graphical element in the life panel.
     *
     * @return The last GameObject in the staticLives list.
     */
    public GameObject getLastElement() {
        return staticLives.get(staticLives.size() - 1);
    }

    /**
     * Updates the life panel display when a life is lost or gained.
     * The numeric display is updated, and a graphical heart is removed or added as necessary.
     *
     * @param strikes The current life counter.
     * @param heart   An optional heart GameObject to add when gaining a life (can be null).
     */
    public void updateLives(Counter strikes, GameObject heart) {
        numericRenderable.setString(String.valueOf(strikes.value()));
        updateColor(strikes);

        if (heart != null) {
            setExtraHeartPosition(heart);
            staticLives.add(heart);
            return;
        }

        // Remove a heart when a life is lost
        staticLives.remove(staticLives.get(strikes.value() + 1));
    }

    /**
     * Adjusts the position of an extra heart to appear to the right of the last heart.
     *
     * @param heart The GameObject representing the extra heart to be positioned.
     */
    private void setExtraHeartPosition(GameObject heart) {
        Vector2 lastHeartPosition = getLastElement().getTopLeftCorner();
        heart.setTopLeftCorner(lastHeartPosition.add(new Vector2(
                (HEART_DIMENSIONS.x() + HEART_PADDING), 0)));
    }

    /**
     * Updates the color of the numeric display based on the remaining lives.
     *1
     * @param strikes The current life counter.
     */
    private void updateColor(Counter strikes) {
        if (strikes.value() >= 3) {
            numericRenderable.setColor(Color.GREEN);
        } else if (strikes.value() == 2) {
            numericRenderable.setColor(Color.YELLOW);
        } else {
            numericRenderable.setColor(Color.RED);
        }
    }
}
