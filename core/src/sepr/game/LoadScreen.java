package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoadScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;

    /**
     *
     * @param main for changing to different screens
     */
    public LoadScreen (final Main main) {
        this.main = main;

        this.stage = new Stage(){
            @Override
            public boolean keyUp(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) { // change back to the menu screen if the player presses esc
                    main.setMenuScreen();
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

    }

    // TODO Implement setupSelectSaveTable()
    private Table setupSelectSaveTable() {
        return null;
    }

    // TODO Finish implementing setupUI()
    /**
     * sets up the UI for the load screen
     */
    private void setupUi() {

        // add the menu background
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("uiComponents/menuBackground.png"))));

        table.center();
        table.add(WidgetFactory.genMenusTopBar("LOAD GAME")).colspan(2);

        table.row();
        table.add().expand();

        table.row();
        table.add(WidgetFactory.genBottomBar("MAIN MENU", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.setMenuScreen();}

        })).colspan(2);
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
