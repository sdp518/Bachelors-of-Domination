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

    private SpriteBatch batch;
    private OrthographicCamera camera;
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

        Gdx.input.setInputProcessor(this);

        this.wheel = Player.genAttackWheelTexture();

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(1920, 1080);
        camera.position.x = 1920 / 2;
        camera.position.y = 1080 / 2;
        this.viewport = new ScreenViewport(this.camera);

        allocateSectors();
        playGame();
        mapPix.put(1, map.hesEastPix1);
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
        //batch.draw(wheel, 0, 0);
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
    public boolean keyPressed;
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
        int hesEast1Pixel = map.hesEastPix1.getPixel(screenX, screenY);
        int hesEast2Pixel = map.hesEastPix2.getPixel(screenX, screenY);
        int hesEast3Pixel = map.hesEastPix3.getPixel(screenX, screenY);
        int hesEast4Pixel = map.hesEastPix4.getPixel(screenX, screenY);
        int halifax1Pixel = map.halifaxPix1.getPixel(screenX, screenY);
        int halifax2Pixel = map.halifaxPix2.getPixel(screenX, screenY);
        int halifax3Pixel = map.halifaxPix3.getPixel(screenX, screenY);
        int halifax4Pixel = map.halifaxPix4.getPixel(screenX, screenY);
        int derwent1Pixel = map.derwentPix1.getPixel(screenX, screenY);
        int derwent2Pixel = map.derwentPix2.getPixel(screenX, screenY);
        int derwent3Pixel = map.derwentPix3.getPixel(screenX, screenY);
        int derwent4Pixel = map.derwentPix4.getPixel(screenX, screenY);
        int alcuin1Pixel = map.alcuinPix1.getPixel(screenX, screenY);
        int alcuin2Pixel = map.alcuinPix2.getPixel(screenX, screenY);
        int alcuin3Pixel = map.alcuinPix3.getPixel(screenX, screenY);
        int vanbrugh1Pixel = map.vanbrughPix1.getPixel(screenX, screenY);
        int vanbrugh2Pixel = map.vanbrughPix2.getPixel(screenX, screenY);
        int vanbrugh3Pixel = map.vanbrughPix3.getPixel(screenX, screenY);
        int vanbrugh4Pixel = map.vanbrughPix4.getPixel(screenX, screenY);
        int wentworth1Pixel = map.wentworthPix1.getPixel(screenX, screenY);
        int wentworth2Pixel = map.wentworthPix2.getPixel(screenX, screenY);
        int james1Pixel = map.jamesPix1.getPixel(screenX, screenY);
        int james2Pixel = map.jamesPix2.getPixel(screenX, screenY);
        int james3Pixel = map.jamesPix3.getPixel(screenX, screenY);
        int james4Pixel = map.jamesPix4.getPixel(screenX, screenY);
        int neutral1Pixel = map.neutralPix1.getPixel(screenX, screenY);
        int neutral2Pixel = map.neutralPix2.getPixel(screenX, screenY);
        int neutral3Pixel = map.neutralPix3.getPixel(screenX, screenY);
        int neutral4Pixel = map.neutralPix4.getPixel(screenX, screenY);
        int neutral5Pixel = map.neutralPix5.getPixel(screenX, screenY);
        int neutral6Pixel = map.neutralPix6.getPixel(screenX, screenY);
        if(hesEast1Pixel != -256){
            map.changeSectorColor(0, "green");
            System.out.println("HIT HES1");
        } else if(hesEast2Pixel != -256) {
            map.changeSectorColor(1, "blue");
            System.out.println("HIT HES2");
        } else if(hesEast3Pixel !=-256) {
            map.changeSectorColor(2, "green");
            System.out.println("HIT HES3");
        } else if(hesEast4Pixel != -256) {
            map.changeSectorColor(3, "blue");
            System.out.println("HIT HES4");
        } else if(halifax1Pixel != -256) {
            System.out.println("HIT HALIFAX1");
            map.changeSectorColor(4, "green");
        } else if(halifax2Pixel != -256) {
            System.out.println("HIT HALIFAX2");
            map.changeSectorColor(5, "blue");
        } else if(halifax3Pixel != -256) {
            System.out.println("HIT HALIFAX3");
            map.changeSectorColor(6, "green");
        } else if(halifax4Pixel != -256) {
            System.out.println("HIT HALIFAX4");
            map.changeSectorColor(7, "blue");
        } else if(derwent1Pixel != -256) {
            System.out.println("HIT DERWENT1");
        } else if(derwent2Pixel != -256) {
            System.out.println("HIT DERWENT2");
        } else if(derwent3Pixel != -256) {
            System.out.println("HIT DERWENT3");
        } else if(derwent4Pixel != -256) {
            System.out.println("HIT DERWENT4");
        } else if(alcuin1Pixel != -256) {
            System.out.println("HIT ALCUIN1");
        } else if(alcuin2Pixel != -256) {
            System.out.println("HIT ALCUIN2");
        } else if(alcuin3Pixel != -256) {
            System.out.println("HIT ALCUIN3");
        } else if(vanbrugh1Pixel != -256) {
            System.out.println("HIT VANBRUGH1");
        } else if(vanbrugh2Pixel != -256) {
            System.out.println("HIT VANBRUGH2");
        } else if(vanbrugh3Pixel != -256) {
            System.out.println("HIT VANBRUGH3");
        } else if(vanbrugh4Pixel != -256) {
            System.out.println("HIT VANBRUGH4");
        } else if(wentworth1Pixel != -256) {
            System.out.println("HIT WENTWORTH1");
        } else if(wentworth2Pixel != -256) {
            System.out.println("HIT WENTWORTH2");
        } else if(james1Pixel != -256) {
            System.out.println("HIT JAMES1");
        } else if(james2Pixel != -256) {
            System.out.println("HIT JAMES2");
        } else if(james3Pixel != -256) {
            System.out.println("HIT JAMES3");
        } else if(james4Pixel != -256) {
                System.out.println("HIT JAMES4");
        } else if(neutral1Pixel != -256) {
            System.out.println("HIT NEUTRAL1");
        } else if(neutral2Pixel != -256) {
            System.out.println("HIT NEUTRAL2");
        } else if(neutral3Pixel != -256) {
            System.out.println("HIT NEUTRAL3");
        } else if(neutral4Pixel != -256) {
            System.out.println("HIT NEUTRAL4");
        } else if(neutral5Pixel != -256) {
            System.out.println("HIT NEUTRAL5");
        } else if(neutral6Pixel != -256) {
            System.out.println("HIT NEUTRAL6");
        } else {
            System.out.println("MISS!");
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
