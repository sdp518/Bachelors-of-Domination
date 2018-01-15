package sepr.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sun.tools.jconsole.Tab;

public abstract class Phase extends Stage {

    protected GameScreen gameScreen;
    protected Map map;
    protected Player currentPlayer;

    private Table table;
    private Label bottomBarRightPart;
    private Table bottomBarLeftPart;
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
        TextButton endPhaseButton = WidgetFactory.genEndPhaseButton("END PHASE");
        endPhaseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.advancePhase();
            }
        });
        bottomBarRightPart = WidgetFactory.genGameHUDBottomBarRightPart("INIT");
        bottomBarLeftPart = WidgetFactory.genGameHUDBottomBarLeftPart(GameSetupScreen.CollegeName.ALCUIN, "Player1", "Troops Available: 13", "Turn Timer: " + gameScreen.getTurnTimeElapsed());

        table.top().center();
        table.add(WidgetFactory.genGameHUDTopBar(turnPhase, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //main.setMenuScreen();
            }
        })).colspan(2).expandX().height(60).width(910);

        table.row();
        table.add(new Table()).expand();

        Table subTable = new Table();

        subTable.bottom();
        subTable.add(bottomBarLeftPart).height(190).width(250);
        subTable.add(bottomBarRightPart).bottom().expandX().fillX().height(60);

        table.row();
        table.add(subTable).expandX().fill();
        table.bottom().right();
        table.add(endPhaseButton).fill().height(60).width(170);

        setBottomBarText(null);
    }


    /**
     * Sets the bar at the bottom of the HUD to the details of the sector currently hovered over
     * If no sector is being hovered then displays "Mouse over a sector to see further details"
     * @param sector the sector of details to be displayed
     */
    public void setBottomBarText(Sector sector) {
        if (sector == null) {
            this.bottomBarRightPart.setText("Mouse over a sector to see further details");
        } else {
            this.bottomBarRightPart.setText(sector.getDisplayName() + " - " + "Owned By: " + sector.getOwnerId() + " - " + "Grants +" + sector.getReinforcementsProvided() + " Troops");
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
