package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class GameScreen implements Screen{
    private Main main;
    private Map map;
    private HashMap<Integer, Player> players; // player id mapping to the relevant player
    private boolean turnTimerEnabled;
    private boolean turnTimerPaused;
    private int maxTurnTime;
    private int turnTimeElapsed;
    private Integer[] turnOrder; // array of player ids in order of players' turns;
    private int currentPlayer; // index of current player
    private Texture wheel;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    /**
     * Performs the game's initial setup
     * @param main used to change screen
     * @param players hashmap of the players in this game
     * @param turnTimerEnabled should players turn be limitted
     * @param maxTurnTime time elapsed in current turn, irrelevant if turn timer not enabled
     */
    public GameScreen(Main main, HashMap<Integer, Player> players, boolean turnTimerEnabled, int maxTurnTime) {
        this.main = main;
        this.players = players;
        this.map = new Map();
        this.turnTimerEnabled = turnTimerEnabled;
        this.turnTimerPaused = false;
        this.maxTurnTime = maxTurnTime;
        this.turnTimeElapsed = 0;
        this.turnOrder = this.players.keySet().toArray(new Integer[0]);
        this.currentPlayer = 0;


        this.wheel = Player.genAttackWheelTexture(30, 2, 1);

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(1920, 1080);
        this.viewport = new ScreenViewport(this.camera);

        allocateSectors();
        playGame();
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

    /**
     *
     * @return returns the x position of the mouse with respect to the camera position and zoom
     */
    public float getMouseX() {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
    }

    /**
     *
     * @return returns the y position of the mouse with respect to the camera position and zoom
     */
    public float getMouseY() {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //all game content to be drawn here
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            camera.translate(-Gdx.input.getDeltaX() * camera.zoom, Gdx.input.getDeltaY() * camera.zoom, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            camera.zoom -= 0.01;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            camera.zoom += 0.01;
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(batch);
        batch.draw(wheel, 400, 400);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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

    }
}
