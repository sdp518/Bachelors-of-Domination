package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import javafx.scene.control.*;
import javafx.util.Pair;

import javax.xml.soap.Text;
import java.util.HashMap;

/**
 * Created by Dom's Surface Mark 2 on 28/11/2017.
 */
public class WidgetFactory {

    private static Texture basicButtonTexture;
    private static Texture mainMenuTopBarTexture;
    private static Texture bottomBarTexture;
    private static Texture mapGraphicTexture;
    private static Texture labelTexture;
    private static Texture optionsGraphicTexture;

    private static Texture sliderBarTexture;
    private static Texture sliderKnobTexture;

    private static Texture onSwitchTexture;
    private static Texture offSwitchTexture;
  
    private static Texture gameHUDBottomBarRightPartTexture;
    private static Texture gameHUDTopBarTexture;
    private static Texture endPhaseBtnTexture;

    private static Texture playerLabelTexture;
    private static Texture playerLeftBtnTexture;
    private static Texture playerRightBtnTexture;
    private static Texture menuBtnLabelTexture;
    private static Texture collegeLeftBtnTexture;
    private static Texture collegeRightBtnTexture;
    private static Texture startGameBtnTexture;
    private static Texture nameBoxLabelTexture;

    private static Texture alcuinLogoTexture;
    private static Texture derwentLogoTexture;
    private static Texture halifaxLogoTexture;
    private static Texture hesEastLogoTexture;
    private static Texture jamesLogoTexture;
    private static Texture uniOfYorkLogoTexture;
    private static Texture vanbrughLogoTexture;
    private static Texture wentworthLogoTexture;

    private static BitmapFont fontBig;
    private static BitmapFont fontSmall;

    private static Skin skin;

    public WidgetFactory() {
        setupFont();

        basicButtonTexture = new Texture("ui/HD-assets/Menu-Button-Full.png");
        mainMenuTopBarTexture = new Texture("ui/HD-assets/Main-Menu-Top-Bar.png");
        bottomBarTexture = new Texture("ui/HD-assets/Bottom-Bar.png");
        mapGraphicTexture = new Texture("ui/HD-assets/Main-Menu-Map.png");
        labelTexture = new Texture("ui/HD-assets/Menu-Button.png");
        optionsGraphicTexture = new Texture("ui/HD-assets/General-Jack.png");

        sliderBarTexture = new Texture("ui/sliderBar.png");
        sliderKnobTexture = new Texture("ui/sliderKnob.png");

        onSwitchTexture = new Texture("ui/HD-assets/On-Switch.png");
        offSwitchTexture = new Texture("ui/HD-assets/Off-Switch.png");

        playerLabelTexture = new Texture("ui/HD-assets/Player-Label.png");
        playerLeftBtnTexture = new Texture("ui/HD-assets/Player-Left-Button-Full.png");
        playerRightBtnTexture = new Texture("ui/HD-assets/Player-Right-Button-Full.png");
        menuBtnLabelTexture = new Texture("ui/HD-assets/Menu-Button.png");
        collegeLeftBtnTexture = new Texture("ui/HD-assets/College-Left-Button.png");
        collegeRightBtnTexture = new Texture("ui/HD-assets/College-Right-Button.png");
        startGameBtnTexture = new Texture("ui/HD-assets/Start-Game-Button-Full.png");
        nameBoxLabelTexture = new Texture("ui/HD-assets/Game-Setup-Name-Box.png");

        alcuinLogoTexture = new Texture("ui/HD-assets/alcuin-logo.png");
        derwentLogoTexture = new Texture("ui/HD-assets/derwent-logo.png");
        halifaxLogoTexture = new Texture("ui/HD-assets/halifax-logo.png");
        hesEastLogoTexture = new Texture("ui/HD-assets/hes-east-logo.png");
        jamesLogoTexture = new Texture("ui/HD-assets/james-logo.png");
        uniOfYorkLogoTexture = new Texture("ui/HD-assets/uni-of-york-logo.png");
        vanbrughLogoTexture = new Texture("ui/HD-assets/vanbrugh-logo.png");
        wentworthLogoTexture = new Texture("ui/HD-assets/wentworth-logo.png");

        gameHUDBottomBarRightPartTexture = new Texture("ui/HD-assets/HUD-Bottom-Bar-Right-Part.png");
        gameHUDTopBarTexture = new Texture("ui/HD-assets/HUD-Top-Bar.png");
        endPhaseBtnTexture = new Texture("ui/HD-assets/End-Phase-Button.png");

         skin = new Skin(Gdx.files.internal("ui/dialogBox/skin/uiskin.json"));
    }

    private void setupFont() {
        FileHandle alteDinBig = new FileHandle("ui/Font/Alte-DIN-Big.fnt");
        FileHandle alteDinSmall = new FileHandle("ui/Font/Alte-DIN-Small.fnt");
        fontBig = new BitmapFont(alteDinBig);
        fontSmall = new BitmapFont(alteDinSmall);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param title String to be used at the top of the dialog box
     * @param message String to be used as the content of the dialog
     * @param stage The stage to draw the box onto
     */
    public static void errorDialogBox(String title, String message, Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/dialogBox/skin/uiskin.json"));
        Dialog dialog = new Dialog(title, skin) {
            protected void result(Object object) {
                // object is the button pressed
            }
        };
        dialog.text(message);
        dialog.button("Ok", 1L);
        dialog.show(stage);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param nextPlayer String to be used to display the name of the next player
     * @param troopsToAllocate Integer to be used to display number of troops
     * @param stage The stage to draw the box onto
     */
    public static void nextTurnDialogBox(String nextPlayer, Integer troopsToAllocate, Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/dialogBox/skin/uiskin.json"));
        Dialog dialog = new Dialog("Next Turn", skin) {
            protected void result(Object object) {
                // object is the button pressed
            }
        };
        dialog.text("Next Player: " + nextPlayer + "\nTroops to Allocate: " + troopsToAllocate);
        dialog.button("Ok", 1L);
        dialog.show(stage);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param stage The stage to draw the box onto
     */
    public static void exitDialogBox(Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/dialogBox/skin/uiskin.json"));
        Dialog dialog = new Dialog("Quit", skin) {
            protected void result(Object object) {
                if (object.toString() != "0"){
                    System.exit(0);
                    }
            }
        };
        dialog.text("Are you sure you want to exit the game?");
        dialog.button("Yes", "1");
        dialog.button("No", "0");
        dialog.show(stage);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param college String to be used to display which college has been conquered
     * @param troops Integer to be used to display the troop bonus
     * @param stage The stage to draw the box onto
     */
    public static void collegeConqueredDialogBox(String college, Integer troops, Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/dialogBox/skin/uiskin.json"));
        Dialog dialog = new Dialog("College Conquered!", skin) {
            protected void result(Object object) {
                // object is the button pressed
            }
        };
        dialog.text("Congratulations! You conquered " + college + "!\nThis college provides: " + troops + " troops");
        dialog.button("Ok", 1L);
        dialog.show(stage);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param college String to be used to display the college
     * @param stage The stage to draw the box onto
     */
    public static void collegeLostDialogBox(String college, Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/dialogBox/skin/uiskin.json"));
        Dialog dialog = new Dialog("College Lost!", skin) {
            protected void result(Object object) {
                // object is the button pressed
            }
        };
        dialog.text("Oh no! You lost " + college + "!");
        dialog.button("Ok", 1L);
        dialog.show(stage);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param bonusTroops Integer used to provide the bonus troops provided by conquered tile
     * @param maxTroops Interger used to provide troops on attacking tile
     * @param stage The stage to draw the box onto
     *
     * @return Integer of troops to be moved to conquered tile
     */
    public static int attackSuccessDialogBox(Integer bonusTroops, Integer maxTroops, Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/dialogBox/skin/uiskin.json"));
        final Slider slider = new Slider(0, (maxTroops - 1), 1, false, skin);
        final Label sliderValue = new Label("0", skin);
        final int[] movedTroops = new int[1];
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sliderValue.setText(new StringBuilder((int)slider.getValue() + ""));
            }
        });
      
        Dialog dialog = new Dialog("Success!", skin) {
          protected void result(Object object) {
            movedTroops[0] = (int)slider.getValue();
        }
        };
        dialog.text("Congratulations! You have earned " + bonusTroops + " bonus troops!\nHow many troops would you like to move?");
        dialog.getContentTable().add(slider).padLeft(20).padRight(20).align(Align.left).expandX();
        dialog.getContentTable().add(sliderValue).padLeft(20).padRight(20).align(Align.right);
        dialog.getContentTable().row();
        dialog.button("Ok", 1L);
        dialog.show(stage);

        return movedTroops[0];
    }
  
    /**
     * @param stage to display the dialog on
     * @param maxAttackers max number of attackers the player chooses to attack with
     * @param defenders how many units are defending
     * @return the number of troops chosen to attack with or 0 if the attack is canceled
     */
    public static int attackDialog(Stage stage, int maxAttackers, int defenders, final int[] attackers) {
        final Slider slider = new Slider(0, maxAttackers, 1, false, skin);
        final Label sliderValue = new Label("0", skin);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sliderValue.setText(new StringBuilder((int)slider.getValue() + ""));
            }
        });
        
        Dialog dialog = new Dialog("Select number of troops to attack with", skin) {
            protected void result(Object object) {
                // object is the button pressed
                if (object.toString().equals("0")) {
                    attackers[0] = 0;
                } else {
                    attackers[0] = (int)slider.getValue();
                }
                System.out.println(attackers[0]);
            }
        };;

        // add labels saying the max number of attackers and how many defenders there are
        dialog.text(new Label("Max attackers: " + maxAttackers, skin)).padLeft(20).padRight(20).align(Align.left);
        dialog.text(new Label("Defenders: " + defenders, skin)).padLeft(20).padRight(20).align(Align.right);

        dialog.getContentTable().row();

        // add slider and label showing number of units selected
        dialog.getContentTable().add(slider).padLeft(20).padRight(20).align(Align.left).expandX();
        dialog.getContentTable().add(sliderValue).padLeft(20).padRight(20).align(Align.right);

        dialog.getContentTable().row();

        // add buttons for accepting or canceling the selection
        dialog.button("Cancel", 0L).padLeft(20).padRight(40).align(Align.center);
        dialog.button("Ok", 1L).padLeft(40).padRight(20).align(Align.center);

        dialog.show(stage);

        return 82;
    }

    public static TextButton genBasicButton(String buttonText) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); // create style for buttons to use
        style.up = new TextureRegionDrawable(new TextureRegion(basicButtonTexture, 0, 0, 857, 123)); // image for button to use in default state
        style.down = new TextureRegionDrawable(new TextureRegion(basicButtonTexture, 0, 123, 857, 123)); // image for button to use when pressed down
        style.font = fontSmall;

        return new TextButton(buttonText, style);
    }

    public static Table genTopBar(String text){

        Image leftPart = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Left-Part-Top-Bar.png"))));
        Image rightPart = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Right-Part-Top-Bar.png"))));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontBig;
        Label textLabel = new Label(text, style);

        Table topBar = new Table();
        topBar.setDebug(false);
        topBar.left();
        topBar.add(leftPart).height(60);
        topBar.add(textLabel).padRight(20).padLeft(20);
        topBar.add(rightPart).fillX().height(60);

        return topBar;
    }

    public static Table genBottomBar(String buttonText, ChangeListener changeListener){

        Image leftPart = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Left-Part-End-Bottom-Bar.png"))));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = fontSmall;
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Esc-Button-Bottom-Bar.png")));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Esc-Button-Bottom-Bar.png")));
        final TextButton escButton = new TextButton(buttonText, buttonStyle);
        escButton.addListener(changeListener);

        Image centerPart = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Center-Part-Bottom-Bar.png"))));


        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontSmall;
        style.background = new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Right-Part-Bottom-Bar.png")));
        String text = "BACHELORS OF" + "\n" + "DOMINATION";
        Label textLabel = new Label(text, style);
        textLabel.setAlignment(0, 40);

        Table bottomBar = new Table();
        bottomBar.setDebug(false);
        bottomBar.left();
        bottomBar.add(leftPart).height(60).width(45).bottom();
        bottomBar.add(escButton).height((float) 51.5).width(190).bottom();
        bottomBar.add(centerPart).height(60).fillX().bottom();
        bottomBar.add(textLabel).height(120).width(280);

        return bottomBar;
    }

    public static Image genMapGraphic() {
        return new Image(mapGraphicTexture);
    }

    public static Image genOptionsGraphic() {
        return new Image(optionsGraphicTexture);
    }

    /**
     *
     * @param college
     * @return
     */
    public static Drawable genCollegeLogoDrawable(GameSetupScreen.CollegeName college) {
        switch (college) {
            case ALCUIN:
                return new TextureRegionDrawable(new TextureRegion(alcuinLogoTexture));
            case DERWENT:
                return new TextureRegionDrawable(new TextureRegion(derwentLogoTexture));
            case HALIFAX:
                return new TextureRegionDrawable(new TextureRegion(halifaxLogoTexture));
            case HES_EAST:
                return new TextureRegionDrawable(new TextureRegion(hesEastLogoTexture));
            case JAMES:
                return new TextureRegionDrawable(new TextureRegion(jamesLogoTexture));
            case UNI_OF_YORK:
                return new TextureRegionDrawable(new TextureRegion(uniOfYorkLogoTexture));
            case VANBRUGH:
                return new TextureRegionDrawable(new TextureRegion(vanbrughLogoTexture));
            case WENTWORTH:
                return new TextureRegionDrawable(new TextureRegion(wentworthLogoTexture));
        }
        return null;
    }

    /**
     *
     * @param college college to get logo Image off
     * @return an Image of the college logo
     */
    public static Image genCollegeLogoImage(GameSetupScreen.CollegeName college){
        return new Image(genCollegeLogoDrawable(college));
    }


    public static Label genPlayerLabel(String labelText) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontSmall;
        style.background = new TextureRegionDrawable(new TextureRegion(playerLabelTexture));

        Label label = new Label(labelText, style);
        label.setAlignment(Align.center);

        return label;
    }

    public static Label genMenuBtnLabel(String labelText) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontSmall;
        style.background = new TextureRegionDrawable(new TextureRegion(menuBtnLabelTexture));

        return new Label(labelText, style);
    }

    public static Label genNameBoxLabel(String labelText) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontSmall;
        Label label = new Label(labelText, style);
        label.setAlignment(Align.left);

        return label;
    }

    public static TextField playerNameTextField(String name){
        TextField.TextFieldStyle  style = new TextField.TextFieldStyle();
        style.font = fontSmall;
        style.fontColor = new Color(Color.WHITE);

        return new TextField(name, style);
    }

    public static Button genPlayerLeftButton() {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(playerLeftBtnTexture, 0, 0, 111, 123));
        style.down = new TextureRegionDrawable(new TextureRegion(playerLeftBtnTexture, 0, 123, 111, 123));

        return new Button(style);
    }

    public static Button genPlayerRightButton() {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(playerRightBtnTexture, 0, 0, 111, 123));
        style.down = new TextureRegionDrawable(new TextureRegion(playerRightBtnTexture, 0, 123, 111, 123));

        return new Button(style);
    }

    public static Button genCollegeLeftButton() {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(collegeLeftBtnTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(collegeLeftBtnTexture));

        return new Button(style);
    }

    public static Button genCollegeRightButton() {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(collegeRightBtnTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(collegeRightBtnTexture));

        return new Button(style);
    }

    public static TextButton genStartGameButton(String buttonText) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(startGameBtnTexture, 0, 0, 723, 123));
        style.down = new TextureRegionDrawable(new TextureRegion(startGameBtnTexture, 0, 123, 723, 123));
        style.font = fontSmall;

        return new TextButton(buttonText,style);
    }

    public static TextButton genEndPhaseButton(String buttonText){
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(endPhaseBtnTexture, 0,0, 348, 123));
        style.down = new TextureRegionDrawable(new TextureRegion(endPhaseBtnTexture, 0,123, 348, 123));
        style.font = fontSmall;

        return new TextButton(buttonText, style);
    }



    /**
     * Generates the UI widget to be displayed at the bottom of the HUD
     * @param labelText what the bar should say
     * @return
     */
    public static Label genGameHUDBottomBarRightPart(String labelText) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontSmall;
        style.background = new TextureRegionDrawable(new TextureRegion(gameHUDBottomBarRightPartTexture));

        Label label = new Label(labelText, style);
        label.setAlignment(Align.center);

        return label;
    }

    public static Table genGameHUDTopBar(TurnPhaseType turnPhase, ChangeListener changeListener) {
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = fontSmall;
        TextButton exitButton = new TextButton("QUIT", btnStyle);

        exitButton.addListener(changeListener);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = fontSmall;

        String text = "";
        switch (turnPhase) {
            case REINFORCEMENT:
                text = "REINFORCEMENT  -  Attack  -  Movement";
                break;
            case ATTACK:
                text = "Reinforcement  -  ATTACK  -  Movement";
                break;
            case MOVEMENT:
                text = "Reinforcement  -  Attack  -  MOVEMENT";
                break;
        }

        Label label = new Label(text, style);
        label.setAlignment(Align.center);

        Table table = new Table();
        table.background(new TextureRegionDrawable(new TextureRegion(gameHUDTopBarTexture)));
        table.left().add(exitButton).padRight(190).padLeft(20);
        table.add(label).height(60);

        return table;
    }


    /**
     *
     * @param items the list of items that may be selected
     * @return a selector widget styled and using the items provided
     */
    public static SelectBox<String> genStyledSelectBox(String[] items) {
        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
        style.font = fontSmall;
        style.listStyle = new List.ListStyle(fontSmall, Color.BLACK, Color.BLACK, new TextureRegionDrawable(new TextureRegion(sliderBarTexture)));
        style.scrollStyle = new ScrollPane.ScrollPaneStyle(new TextureRegionDrawable(new TextureRegion(sliderKnobTexture)), new TextureRegionDrawable(new TextureRegion(sliderKnobTexture)), new TextureRegionDrawable(new TextureRegion(sliderKnobTexture)), new TextureRegionDrawable(new TextureRegion(sliderKnobTexture)), new TextureRegionDrawable(new TextureRegion(sliderKnobTexture)));

        SelectBox<String> selectBox = new SelectBox<String>(style);
        selectBox.setItems(items);
        return selectBox;
    }

    /**
     *
     * @return a toggleable on off switch widget
     */
    public static CheckBox genOnOffSwitch() {
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
        style.font = new BitmapFont();
        style.checkboxOn = new TextureRegionDrawable(new TextureRegion(onSwitchTexture));
        style.checkboxOff = new TextureRegionDrawable(new TextureRegion(offSwitchTexture));

        return new CheckBox(null, style);
    }

    /**
     * Generates a slider widget that is styled for use in the options screen
     * @return a slider
     */
    public static Slider genStyledSlider() {
        Slider.SliderStyle style = new Slider.SliderStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(sliderBarTexture));
        style.knob = new TextureRegionDrawable(new TextureRegion(sliderKnobTexture));
        style.knobDown = new TextureRegionDrawable(new TextureRegion(sliderKnobTexture));
        style.knobOver = new TextureRegionDrawable(new TextureRegion(sliderKnobTexture));

        return new Slider(0f, 1f, 0.01f, false, style);
    }

    public static BitmapFont getFontBig() {
        return fontBig;
    }

    public static BitmapFont getFontSmall() {
        return fontSmall;
    }
}
