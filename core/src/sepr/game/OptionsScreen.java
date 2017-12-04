package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class OptionsScreen implements Screen {
    private Main main;
    private Stage stage;
    private Table table;

    private Slider musicSlider;
    private Slider fxSlider;
    private SelectBox<String> resolutionSelector;
    private CheckBox fullscreenSwitch;
    private CheckBox colourblindModeSwitch;

    public OptionsScreen(Main main) {
        this.main = main;

        this.stage = new Stage();
        this.stage.setViewport(new ScreenViewport());
        this.table = new Table();

        this.stage.addActor(table);
        this.table.setFillParent(true);
        this.table.setDebug(true);
        this.setupUi();
    }

    private Table setupOptionsTable() {
        musicSlider = WidgetFactory.genStyledSlider();
        fxSlider = WidgetFactory.genStyledSlider();
        resolutionSelector = WidgetFactory.genStyledSelectBox(new String[] {"x", "y", "z"});
        fullscreenSwitch = WidgetFactory.genOnOffSwitch();
        colourblindModeSwitch = WidgetFactory.genOnOffSwitch();

        Table table = new Table();
        table.left();
        table.add(WidgetFactory.genStyledLabel("Music Volume")).fill().pad(30);
        table.right();
        table.add(musicSlider);

        table.row();
        table.left();
        table.add(WidgetFactory.genStyledLabel("FX Volume")).fill().pad(30);
        table.right();
        table.add(fxSlider);

        table.row();
        table.left();
        table.add(WidgetFactory.genStyledLabel("Resolution")).fill().pad(30);
        table.right();
        table.add(resolutionSelector);

        table.row();
        table.left();
        table.add(WidgetFactory.genStyledLabel("Fullscreen")).fill().pad(30);
        table.right();
        table.add(fullscreenSwitch);

        table.row();
        table.left();
        table.add(WidgetFactory.genStyledLabel("Colourblind Mode")).fill().pad(30);
        table.right();
        table.add(colourblindModeSwitch);

        table.row();
        table.left();
        table.add(WidgetFactory.genBasicButton("Cancel")).fill().pad(30);
        table.right();
        table.add(WidgetFactory.genBasicButton("Accept"));

        return table;
    }

    private void setupUi() {
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("ui/background.png"))));

        table.center();
        table.add(WidgetFactory.genTopBarGraphic()).colspan(2).fillX();

        table.row();
        table.add(setupOptionsTable()).expand();

        table.add(WidgetFactory.genOptionsGraphic()).pad(30);

        table.row();
        table.add(WidgetFactory.genBottomBarGraphic()).colspan(2).fillX();
    }

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
