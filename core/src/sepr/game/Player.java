package sepr.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public abstract class Player {
    private int id;
    private static Map map;
    private String collegeName;
    private int troopsToAllocate;
    private Color sectorColour; // what colour to shade sectors owned by the player

    /**
     *
     * @param id player's unique identifier
     * @param map map for the player to carry out actions upon
     * @param collegeName display name for this player
     */
    public Player(int id, Map map, String collegeName) {
        this.id = id;
        this.map = map;
        this.collegeName = collegeName;
        this.troopsToAllocate = 0;
    }

    /**
     * Let player attack enemy territories adjacent to its own
     */
    protected abstract void processAttackPhase();

    /**
     * Let player move troops between adjacent friendly territories
     */
    protected abstract void processMovePhase();

    /**
     * Let the player choose where to allocate their additional reinforcements
     * @param amount thr amount of toops available to allocate
     * @return how many troops remaining they may allocate in a future turn
     */
    protected abstract int processAllocationPhase(int amount);

    /**
     * resolves a conflict between two territories
     * @throws IllegalArgumentException if a player is attacking a sector it owns
     * @throws IllegalArgumentException if sectors are not connected
     * @param attackingSectorId id of sector carrying out the attack
     * @param defendingSectorId id of sector being attacked
     * @param amountOfTroopsAttacking number of troops that are being used to attack with
     */
    private static void resolveCombat(int attackingSectorId, int defendingSectorId, int amountOfTroopsAttacking) {

    }

    /**
     * Generates the attack wheel with sectors in the ratio of the parameters
     * @param miss relative size of miss sector
     * @param hit relative size of hit sector
     * @param crit relative size of critical hit sector
     * @return returns a 500x500 texture of the attack wheel
     */
    public static Texture genAttackWheelTexture(int miss, int hit, int crit) {
        FrameBuffer wheel = new FrameBuffer(Pixmap.Format.RGBA8888, 1000, 500, false); // width is double height otherwise texture appears squished???
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        wheel.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(250, 250, 220);

        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.arc(250, 250, 200, 0, (miss / (float)(miss + hit + crit)) * 360);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.arc(250, 250, 200, (miss / (float)(miss + hit + crit)) * 360, (hit / (float)(miss + hit + crit)) * 360);

        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.arc(250, 250, 200, ((hit / (float)(miss + hit + crit)) + (miss / (float)(miss + hit + crit))) * 360, (crit / (float)(miss + hit + crit)) * 360);

        shapeRenderer.end();
        wheel.end();

        return wheel.getColorBufferTexture();
    }
}
