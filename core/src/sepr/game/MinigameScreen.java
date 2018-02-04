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

    /**
     *
     * @param main for changing to different screens
     */
    public MinigameScreen (final Main main) {
        this.main = main;
        this.random = new Random();

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
        this.slotMachine();

    }

    // TODO Implement slotTable()
    private void slotMachine() {
        this.slotStage = new Stage();

        Table slotTable = new Table();
        slotTable.setDebug(false);
        slotTable.setFillParent(true);

        Texture slotOne = new Texture("uiComponents/minigame1.png");
        Texture slotTwo = new Texture("uiComponents/minigame2.png");
        Texture slotThree = new Texture("uiComponents/minigame3.png");
        Texture slotFour = new Texture("uiComponents/minigame4.png");

        Image a = new Image(slotOne);
        Image b = new Image(slotTwo);
        Image c = new Image(slotThree);
        Image d = new Image(slotFour);
        Image e = new Image(slotOne);
        Image f = new Image(slotTwo);
        Image g = new Image(slotThree);
        Image h = new Image(slotFour);
        Image i = new Image(slotOne);
        Image j = new Image(slotTwo);
        Image k = new Image(slotThree);
        Image l = new Image(slotFour);

        Image[] imagesSlotOne = new Image[] {a, b, c, d} ;
        Image[] imagesSlotTwo = new Image[] {e, f, g, h} ;
        Image[] imagesSlotThree = new Image[] {i, j, k, l} ;

        slotTable.row().center().padBottom(100);
        slotTable.add(imagesSlotOne[random.nextInt(4)]);
        slotTable.add(imagesSlotTwo[random.nextInt(4)]).padLeft(40).padRight(40);
        slotTable.add(imagesSlotThree[random.nextInt(4)]);

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
                slotMachine();
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
