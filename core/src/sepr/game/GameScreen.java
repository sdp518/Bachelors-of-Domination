package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import java.util.HashMap;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class GameScreen implements Screen{
    private Main main;

    private Stage stage;
    private Table table;

    private Map map;
    private HashMap<Integer, Player> players; // player id mapping to the relevant player
    private HashMap<Integer, Boolean> keysDown; // mapping from key, (Input.Keys), to whether it has been pressed down

    private boolean turnTimerEnabled;
    private boolean turnTimerPaused;
    private int maxTurnTime;
    private int turnTimeElapsed;
    private Integer[] turnOrder; // array of player ids in order of players' turns;
    private int currentPlayer; // index of current player

    /**
     * Performs the game's initial setup
     * @param main used to change screen
     * @param players hashmap of the players in this game
     * @param turnTimerEnabled should players turn be limitted
     * @param maxTurnTime time elapsed in current turn, irrelevant if turn timer not enabled
     */
    public GameScreen(Main main, HashMap<Integer, Player> players, boolean turnTimerEnabled, int maxTurnTime) {
        this.main = main;

        this.stage = new Stage();
        this.stage.setViewport(new ScreenViewport());
        this.stage.getCamera().translate(1920/2, 1080/2, 0);

        this.map = new Map();
        this.stage.addActor(map);

        this.players = players;

        this.keysDown = new HashMap<Integer, Boolean>();
        this.keysDown.put(Input.Keys.W, false);
        this.keysDown.put(Input.Keys.A, false);
        this.keysDown.put(Input.Keys.S, false);
        this.keysDown.put(Input.Keys.D, false);

        this.turnTimerEnabled = turnTimerEnabled;
        this.turnTimerPaused = false;
        this.maxTurnTime = maxTurnTime;
        this.turnTimeElapsed = 0;
        this.turnOrder = this.players.keySet().toArray(new Integer[0]);
        this.currentPlayer = 0;

        this.stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                super.keyDown(event, keycode);
                if (keycode == Input.Keys.W) {
                    keysDown.put(Input.Keys.W, true);
                }
                if (keycode == Input.Keys.S) {
                    keysDown.put(Input.Keys.S, true);
                }
                if (keycode == Input.Keys.A) {
                    keysDown.put(Input.Keys.A, true);
                }
                if (keycode == Input.Keys.D) {
                    keysDown.put(Input.Keys.D, true);
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                super.keyUp(event, keycode);
                if (keycode == Input.Keys.W) {
                    keysDown.put(Input.Keys.W, false);
                }
                if (keycode == Input.Keys.S) {
                    keysDown.put(Input.Keys.S, false);
                }
                if (keycode == Input.Keys.A) {
                    keysDown.put(Input.Keys.A, false);
                }
                if (keycode == Input.Keys.D) {
                    keysDown.put(Input.Keys.D, false);
                }
                return true;
            }
        });
        this.stage.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Vector2 stageCoord = stage.stageToScreenCoordinates(new Vector2(x, y)); // works @ 1920x1080 but performs incorrectly if resized or cam moved
                System.out.println(stageCoord.x + "  " + stageCoord.y);
                map.detectSectorClick((int) stageCoord.x, (int) stageCoord.y);
            }
        });

        setupUi();

        allocateSectors();
        playGame();
    }

    private void setupUi() {
        this.table = new Table();
        this.table.setFillParent(true);
        this.stage.addActor(table);
        this.table.setDebug(true);

        Texture buttons = new Texture("ui/buttons.png"); // texture sheet for buttons
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); // create style for buttons to use
        style.up = new TextureRegionDrawable(new TextureRegion(buttons, 0, 0, 400, 150)); // image for button to use in default state
        style.down = new TextureRegionDrawable(new TextureRegion(buttons, 0, 150, 400, 150)); // image for button to use when pressed down
        style.font = new BitmapFont(); // set button font to the default Bitmap Font

        final TextButton startGameBtn = new TextButton("New Game", style);
        table.add(startGameBtn);
    }

    private void playGame() {

    }

    /**
     * Allocate sectors to each player in a balanced mannor
     */
    private void allocateSectors() {

    }

    /**
     *
     * @param playerId player's whos turn it is to be carried out
     */
    private void executePlayerTurn(int playerId) {

    }

    /**
     * Method called when map class returns a winner when checkForWinner called
     * @param winnerId id of the winning player
     */
    private void gameOver(int winnerId) {

    }

    /**
     * Writes the game state to a file
     */
    private void saveGameState() {

    }

    /**
     * Reads the given string and setsup the game state from this
     * @param gameState
     */
    private void loadGameState(String gameState) {

    }

    private void controlCamera() {
        if (this.keysDown.get(Input.Keys.W)) {
            this.stage.getCamera().translate(0, 4, 0);
        }
        if (this.keysDown.get(Input.Keys.S)) {
            this.stage.getCamera().translate(0, -4, 0);
        }
        if (this.keysDown.get(Input.Keys.A)) {
            this.stage.getCamera().translate(-4, 0, 0);
        }
        if (this.keysDown.get(Input.Keys.D)) {
            this.stage.getCamera().translate(4, 0, 0);
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        this.controlCamera();
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
        this.stage.getCamera().viewportWidth = width;
        this.stage.getCamera().viewportHeight = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
