package sepr.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class Phase extends Stage {

    protected GameScreen gameScreen;
    protected Map map;
    protected Player currentPlayer;

    private Table table;
    private Label bottomBar;
    private TurnPhaseType turnPhase;


    public Phase(GameScreen gameScreen, Map map, TurnPhaseType turnPhase) {
        //super();
        this.setViewport(new ScreenViewport());

        this.gameScreen = gameScreen;
        this.map = map;

        this.turnPhase = turnPhase;


        this.table = new Table();
        this.table.setFillParent(true); // make ui table fill the entire screen
        this.addActor(table);
        this.table.setDebug(true); // enable table drawing for ui debug

        this.setupUi();
    }


    private void setupUi() {
        TextButton endPhaseButton = WidgetFactory.genBasicButton("End Phase");
        endPhaseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.advancePhase();
            }
        });
        bottomBar = WidgetFactory.genGameHUDBottomBar("INIT");

        table.top().center();
        table.add(WidgetFactory.genPhaseIndicator(turnPhase)).colspan(2).expandX().fill(0.9f, 0);

        table.row();
        table.add(new Table()).expand();

        Table subTable = new Table();

        subTable.center().bottom();
        subTable.add(bottomBar).expandX().fill(0.9f, 0);

        table.row();
        table.add(subTable).expandX().fill();
        table.bottom().right();
        table.add(endPhaseButton).fill();

        setBottomBarText(null);
    }


    /**
     * Sets the bar at the bottom of the HUD to the details of the sector currently hovered over
     * If no sector is being hovered then displays "Mouse over a sector to see further details"
     * @param sector the sector of details to be displayed
     */
    public void setBottomBarText(Sector sector) {
        if (sector == null) {
            this.bottomBar.setText("Mouse over a sector to see further details");
        } else {
            this.bottomBar.setText(sector.getDisplayName() + " - " + "Owned By: " + sector.getOwnerId() + " - " + "Grants +" + sector.getReinforcementsProvided() + " Troops");
        }
    }

    public void enterPhase(Player player) {
        this.currentPlayer = player;
    }

    /**
     * method for tidying up phase for next player to use
     */
    public void endPhase () {
        this.currentPlayer = null;
    }

    @Override
    public void draw() {
        gameScreen.getGameplayBatch().begin();
        visualisePhase(gameScreen.getGameplayBatch());
        gameScreen.getGameplayBatch().end();

        super.draw();
    }

    public abstract void visualisePhase(SpriteBatch batch);
}
