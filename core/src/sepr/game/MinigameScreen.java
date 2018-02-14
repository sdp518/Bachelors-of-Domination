package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

public class MinigameScreen implements Screen {

    private GameScreen gameScreen;
    private Stage stage;
    private Table table;

    private Stage slotStage, launchStage, richardStage;
    private Random random;

    private boolean isSpinning;
    private int slotOneSpins, slotTwoSpins, slotThreeSpins;
    private int slotOneCurrent, slotTwoCurrent, slotThreeCurrent;

    private Image slotMachine, launchBtn, launchBtnPressed;
    private Image a, b, c, d, e, f, g, h, i, j, k, l;

    private Image[] imagesSlotOne, imagesSlotTwo, imagesSlotThree;

    private Image richardLaunch, richardFail;
    private MoveToAction moveUp, moveDown;

    private boolean gameFinished;


    /**
     *
     * @param gameScreen for changing to different screens
     */
    public MinigameScreen (final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.stage = new Stage();
        this.random = new Random();

        this.gameFinished = false;

        this.slotStage = new Stage();

        this.isSpinning = false;
        this.slotOneSpins = 0;
        this.slotTwoSpins = 0;
        this.slotThreeSpins = 0;

        this.slotMachine = new Image(new Texture("uiComponents/minigame/slotMachine.png"));
        this.launchBtn = new Image(new Texture("uiComponents/minigame/launchButton.png"));
        this.launchBtnPressed = new Image(new Texture("uiComponents/minigame/launchButtonPressed.png"));

        this.launchBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isSpinning = true;
                setupLaunchStage();
                for (Action a : richardLaunch.getActions()) {
                    richardLaunch.removeAction(a);
                }
                richardLaunch.addAction(moveDown);
            }
        });

        richardStage = new Stage();


        Texture imgOne = new Texture("uiComponents/minigame/minigame1.png");
        Texture imgTwo = new Texture("uiComponents/minigame/minigame2.png");
        Texture imgThree = new Texture("uiComponents/minigame/minigame3.png");
        Texture imgFour = new Texture("uiComponents/minigame/minigame4.png");

        a = new Image(imgOne);
        b = new Image(imgTwo);
        c = new Image(imgThree);
        d = new Image(imgFour);
        e = new Image(imgOne);
        f = new Image(imgTwo);
        g = new Image(imgThree);
        h = new Image(imgFour);
        i = new Image(imgOne);
        j = new Image(imgTwo);
        k = new Image(imgThree);
        l = new Image(imgFour);

        this.imagesSlotOne = new Image[] {a, b, c, d} ;
        this.imagesSlotTwo = new Image[] {e, f, g, h} ;
        this.imagesSlotThree = new Image[] {i, j, k, l} ;

        //Temp for debug
        this.launchStage = new Stage() {
            @Override
            public boolean keyUp(int keyCode) {
                if ((keyCode == Input.Keys.ESCAPE) && (gameFinished)) { // change back to the menu screen if the player presses esc
                    Gdx.app.exit();
                }
                return super.keyUp(keyCode);
            }
        };

        this.stage.setViewport(new ScreenViewport());
        this.table = new Table();

        this.stage.addActor(table);
        this.table.setFillParent(true);
        this.table.setDebug(false);

        this.setupUi();
        this.slotMachine(random.nextInt(4), random.nextInt(4), random.nextInt(4));

        setupLaunchStage();
        setupRichardStage();
    }

    private void setupLaunchStage() {
        if (launchStage.getActors().size != 0){
            launchStage.getActors().items[0].remove();
        }

        Table launchTable = new Table();
        launchTable.setDebug(false);
        launchTable.setFillParent(true);

        if (isSpinning) {
            launchTable.row().center();
            launchTable.add(launchBtnPressed).colspan(3).padTop(500);
        }
        else {
            launchTable.row().center();
            launchTable.add(launchBtn).colspan(3).padTop(500);
        }

        launchStage.addActor(launchTable);
    }

    private void setupRichardStage() {
        moveUp = new MoveToAction();
        moveUp.setPosition(1150f,0f);
        moveUp.setDuration(0.5f);

        moveDown = new MoveToAction();
        moveDown.setPosition(1150f,-256f);
        moveDown.setDuration(0.5f);

        richardLaunch = new Image(new Texture("uiComponents/minigame/richardLaunch.png"));
        richardFail = new Image(new Texture("uiComponents/minigame/richardFail.png"));

        Image[] richards = new Image[]{richardLaunch, richardFail};

        for (Image richard : richards){
            richard.setPosition(1150,-256);
            richard.setOrigin(richard.getWidth()/2, richard.getHeight()/2);
            richardStage.addActor(richard);
        }

        richardLaunch.addAction(moveUp);
    }

    /**
     * takes the new values as parameters and sets up the UI of the individual slots
     * @param selectOne the new value to display in slot one
     * @param selectTwo the new value to display in slot two
     * @param selectThree the new value to display in slot three
     */
    private void slotMachine(int selectOne, int selectTwo, int selectThree) {
        if (slotStage.getActors().size != 0){
            slotStage.getActors().items[0].remove();
        }

        Table slotTable = new Table();
        slotTable.setDebug(false);
        slotTable.setFillParent(true);

        slotTable.row();
        slotTable.add().expandX().height(180).colspan(3);

        slotTable.row().center();
        slotTable.add(imagesSlotOne[selectOne]).padLeft(250);
        slotTable.add(imagesSlotTwo[selectTwo]).padLeft(20).padRight(20);
        slotTable.add(imagesSlotThree[selectThree]).padRight(250);

        slotTable.row();
        slotTable.add().expand().colspan(3);

        slotStage.addActor(slotTable);
    }

    // TODO Finish implementing setupUI()
    /**
     * sets up the UI for the load screen
     */
    private void setupUi() {

        // add the menu background
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("uiComponents/menuBackground.png"))));

        table.center();
        table.add(WidgetFactory.genMenusTopBar("MINIGAME")).colspan(2).fillX();

        table.row();
        table.add().expandX().height(30);

        table.row();
        table.add(slotMachine);

        table.row();
        table.add().expandX().expandY();

    }

    /**
     * handles the spin of the slot machine, using global flags to track state
     */
    private void handleSpin() {
        int one, two, three;

        one = (slotOneSpins < 50 ? random.nextInt(4) : slotOneCurrent);
        two = (slotTwoSpins < 100 ? random.nextInt(4) : slotTwoCurrent);
        three = (slotThreeSpins < 150 ? random.nextInt(4) : slotThreeCurrent);
        slotOneSpins ++;
        slotTwoSpins ++;
        slotThreeSpins ++;
        slotOneCurrent = one;
        slotTwoCurrent = two;
        slotThreeCurrent = three;
        this.slotMachine(one, two, three);

        // if this was the final spin resets global flags and calls to handle result
        if (slotThreeSpins == 150){
            isSpinning = false;
            setupLaunchStage();
            slotOneSpins = 0;
            slotTwoSpins = 0;
            slotThreeSpins = 0;
            handleResult(one, two, three);
        }

    }

    /**
     * calculates the result of the spin from the chosen values
     * @param one the result from slot one
     * @param two the result from slot two
     * @param three the result from slot three
     */
    private void handleResult(int one, int two, int three) {
        if ((one == two) && (two == three)) {
            // MATCHED THREE
            moveUp.reset();
            //richardLaunch.addAction(moveUp);
            gameFinished = true;
        }
        else if ((one == two) || (one == three) || (two == three)) {
            // MATCHED TWO
            moveUp.reset();
            //richardLaunch.addAction(moveUp);
            gameFinished = true;
        }
        else {
            // MATCHED NONE
            moveUp.reset();
            richardFail.addAction(moveUp);
            gameFinished = true;
        }
    }

    /**
     * change the input processing to be handled by this screen's stage
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(launchStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();

        if (isSpinning) {
            handleSpin();
        }

        this.slotStage.draw();
        this.launchStage.draw();

        richardStage.act();
        richardStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
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
        launchStage.dispose();
        slotStage.dispose();
        richardStage.dispose();
    }

}
