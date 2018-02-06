package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

public class MinigameScreen implements Screen {

    private Main main;
    private Stage stage;
    private Table table;

    private Stage slotStage;
    private Random random;

    private boolean isSpinning;
    private int slotOneSpins;
    private int slotTwoSpins;
    private int slotThreeSpins;
    private int slotOneCurrent;
    private int slotTwoCurrent;
    private int slotThreeCurrent;

    private Image a;
    private Image b;
    private Image c;
    private Image d;
    private Image e;
    private Image f;
    private Image g;
    private Image h;
    private Image i;
    private Image j;
    private Image k;
    private Image l;

    /**
     *
     * @param main for changing to different screens
     */
    public MinigameScreen (final Main main) {
        this.main = main;
        this.random = new Random();

        this.isSpinning = false;
        this.slotOneSpins = 0;
        this.slotTwoSpins = 0;
        this.slotThreeSpins = 0;

        Texture slotOne = new Texture("uiComponents/minigame1.png");
        Texture slotTwo = new Texture("uiComponents/minigame2.png");
        Texture slotThree = new Texture("uiComponents/minigame3.png");
        Texture slotFour = new Texture("uiComponents/minigame4.png");

        a = new Image(slotOne);
        b = new Image(slotTwo);
        c = new Image(slotThree);
        d = new Image(slotFour);
        e = new Image(slotOne);
        f = new Image(slotTwo);
        g = new Image(slotThree);
        h = new Image(slotFour);
        i = new Image(slotOne);
        j = new Image(slotTwo);
        k = new Image(slotThree);
        l = new Image(slotFour);

        //this.stage = new Stage();

        //Temp for debug
        this.stage = new Stage() {
            @Override
            public boolean keyUp(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) { // change back to the menu screen if the player presses esc
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

    }

    // TODO Implement slotTable()
    private void slotMachine(int selectOne, int selectTwo, int selectThree) {
        this.slotStage = new Stage();

        Table slotTable = new Table();
        slotTable.setDebug(false);
        slotTable.setFillParent(true);

        Image[] imagesSlotOne = new Image[] {a, b, c, d} ;
        Image[] imagesSlotTwo = new Image[] {e, f, g, h} ;
        Image[] imagesSlotThree = new Image[] {i, j, k, l} ;

        slotTable.row().center().padBottom(100);
        slotTable.add(imagesSlotOne[selectOne]);
        slotTable.add(imagesSlotTwo[selectTwo]).padLeft(40).padRight(40);
        slotTable.add(imagesSlotThree[selectThree]);

        slotStage.addActor(slotTable);
    }

    // TODO Finish implementing setupUI()
    /**
     * sets up the UI for the load screen
     */
    private void setupUi() {

        final TextButton spinButton = WidgetFactory.genEndPhaseButton();
        spinButton.setText("SPIN");
        spinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isSpinning = true;
            }
        });

        // add the menu background
        table.setDebug(false);

        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("uiComponents/menuBackground.png"))));

        table.center();
        table.add(WidgetFactory.genMenusTopBar("MINIGAME")).colspan(2);

        /*table.row();
        table.add().expandX().height(150);

        table.row();
        table.add(slotMachine());*/

        table.row();
        table.add().expand();

        table.row().center();
        table.add(spinButton);

        table.row();
        table.add().expandX().height(60);

    }

    /**
     * change the input processing to be handled by this screen's stage
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();

        if (isSpinning){
            int one;
            int two;
            int three;

            if (slotThreeSpins == 150){
                isSpinning = false;
                slotOneSpins = 0;
                slotTwoSpins = 0;
                slotThreeSpins = 0;
            }
            else {
                one = (slotOneSpins < 50 ? random.nextInt(4) : slotOneCurrent);
                two = (slotTwoSpins < 100 ? random.nextInt(4) : slotTwoCurrent);
                three = (slotThreeSpins < 150 ? random.nextInt(4) : slotThreeCurrent);
                slotOneSpins ++;
                slotTwoSpins ++;
                slotThreeSpins ++;
                slotOneCurrent = (slotOneSpins==50?one:slotOneCurrent);
                slotTwoCurrent = (slotTwoSpins==100?two:slotTwoCurrent);
                slotThreeCurrent = (slotThreeSpins==150?three:slotThreeCurrent);
                this.slotMachine(one, two, three);
            }
        }

        this.slotStage.draw();
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

    }

}
