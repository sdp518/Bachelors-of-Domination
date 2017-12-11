package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameSetupScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;

    private CheckBox turnTimerSwitch;


    public GameSetupScreen (Main main) {
        this.main = main;

        this.stage = new Stage();
        this.stage.setViewport(new ScreenViewport());

        this.table = new Table();
        this.table.setFillParent(true); // make ui table fill the entire screen
        this.stage.addActor(table);
        this.table.setDebug(false); // enable table drawing for ui debug
        this.setupUi();

    } private Table setUpGameSetupTable() {

            Button playerLeftButton = WidgetFactory.genPlayerLeftButton();
            Button playerRightButton = WidgetFactory.genPlayerRightButton();
            Label playerLabel = WidgetFactory.genPlayerLabel("HUMAN PLAYER");

            Table playerTable = new Table();
            playerTable.setDebug(true);

            //Player 1
            playerTable.left();
            playerTable.add(playerLeftButton).pad(30);
            playerTable.add(playerLabel);
            playerTable.right();
            playerTable.add(playerRightButton).pad(30);

            //Player 2
            playerTable.row();
            playerTable.left();
            playerTable.add(playerLeftButton).pad(30);
            playerTable.add(playerLabel);
            playerTable.right();
            playerTable.add(playerRightButton).pad(30);

            //Player 3
            playerTable.left();
            playerTable.add(playerLeftButton).pad(30);
            playerTable.add(playerLabel);
            playerTable.right();
            playerTable.add(playerRightButton).pad(30);

            //Player 4
            playerTable.row();
            playerTable.left();
            playerTable.add(playerLeftButton).pad(30);
            playerTable.add(playerLabel);
            playerTable.right();
            playerTable.add(playerRightButton).pad(30);

            //Player 5
            playerTable.row();
            playerTable.left();
            playerTable.add(playerLeftButton).pad(30);
            playerTable.add(playerLabel);
            playerTable.right();
            playerTable.add(playerRightButton).pad(30);


            return playerTable;

        }


    private void setupUi() {
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("ui/background.png"))));

        table.center();
        table.add(WidgetFactory.genTopBarGraphic()).colspan(2).fillX();

        table.row();
        table.left();
        table.add(setUpGameSetupTable()).expand();

        table.row();
        table.center();
        table.add(WidgetFactory.genBottomBarGraphic()).colspan(2).fillX();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        main.applyPreferences();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
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
