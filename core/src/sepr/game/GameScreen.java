package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.HashMap;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class GameScreen implements Screen, InputProcessor{
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

    private SpriteBatch batch; // batch for rendering the non-ui graphics, i.e. map
    private OrthographicCamera camera; // camera for rendering non-ui graphics
    private Viewport viewport;

    Color test = new Color(0,0,0,0);
    private HashMap<Integer, Pixmap> mapPix = new HashMap<Integer, Pixmap>();

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
        camera.position.x = 1920 / 2;
        camera.position.y = 1080 / 2;

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
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        //all game content to be drawn here
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-4,0,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(4,0,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0,4,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0,-4,0);
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        map.render(batch);

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
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 mousePos = camera.unproject(new Vector3(screenX, screenY, 0));
        this.map.touchDown(screenX, screenY, pointer, button);

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount == 1){
            camera.zoom += 0.1;
        } else {
            camera.zoom -= 0.1;
        }
        return true;
    }
}
