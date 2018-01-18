package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

public class DialogFactory {

    private static Skin skin;

    public DialogFactory() {
        skin = new Skin(Gdx.files.internal("dialogBox/skin/uiskin.json"));
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param title String to be used at the top of the dialog box
     * @param message String to be used as the content of the dialog
     * @param stage The stage to draw the box onto
     */
    public static void basicDialogBox(String title, String message, Stage stage) {
        Dialog dialog = new Dialog(title, DialogFactory.skin) {
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
        basicDialogBox("Next Turn", "Next Player: " + nextPlayer + "\nTroops to Allocate: " + troopsToAllocate, stage);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param stage The stage to draw the box onto
     */
    public static void exitDialogBox(Stage stage) {
        Dialog dialog = new Dialog("Quit", DialogFactory.skin) {
            protected void result(Object object) {
                if (object.toString().equals("0")){
                    Gdx.app.exit();
                }
            }
        };
        dialog.text("Are you sure you want to exit the game?");
        dialog.button("Yes", "1");
        dialog.button("No", "0");
        dialog.show(stage);
    }

    public static void sectorOwnerChangeDialog(String prevOwner, String newOwner, String sectorName, Stage stage) {
        basicDialogBox("Sector Owner Change", newOwner + " gained " + sectorName + " from " + prevOwner, stage);
    }

    /**
     * Creates a dialog modal in the given stage with an ok button
     *
     * @param bonusTroops Integer used to provide the bonus troops provided by conquered tile
     * @param maxTroops Integer used to provide troops on attacking tile
     * @param troopsMoved 1 index final array for setting value of slider to
     * @param stage The stage to draw the box onto
     */
    public static void attackSuccessDialogBox(Integer bonusTroops, Integer maxTroops, final int[] troopsMoved, String prevOwner, String newOwner, String sectorName, Stage stage) {
        final Slider slider = new Slider(1, (maxTroops - 1), 1, false, DialogFactory.skin);
        slider.setValue(1);
        final Label sliderValue = new Label("1", DialogFactory.skin);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sliderValue.setText(new StringBuilder((int)slider.getValue() + ""));
            }
        });

        Dialog dialog = new Dialog("Success!", DialogFactory.skin) {
          protected void result(Object object) {
            troopsMoved[0] = (int)slider.getValue();
        }
        };
        dialog.text(newOwner + " gained " + sectorName + " from " + prevOwner + "\nYou have earned " + bonusTroops + " bonus troops!\nHow many troops would you like to move to the new sector?");
        dialog.getContentTable().row();

        dialog.getContentTable().add(slider).padLeft(20).padRight(20).align(Align.left).expandX();
        dialog.getContentTable().add(sliderValue).padLeft(20).padRight(20).align(Align.right);

        dialog.getContentTable().row();

        dialog.button("Ok", 1L);
        dialog.show(stage);
    }

    /**
     * @param stage to display the dialog on
     * @param maxAttackers max number of attackers the player chooses to attack with
     * @param defenders how many units are defending
     * @return the number of troops chosen to attack with or 0 if the attack is canceled
     */
    public static void attackDialog(Stage stage, int maxAttackers, int defenders, final int[] attackers) {
        final Slider slider = new Slider(0, maxAttackers, 1, false, DialogFactory.skin);
        final Label sliderValue = new Label("0", DialogFactory.skin);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sliderValue.setText(new StringBuilder((int)slider.getValue() + ""));
            }
        });

        Dialog dialog = new Dialog("Select number of troops to attack with", DialogFactory.skin) {
            protected void result(Object object) {
                // object is the button pressed
                if (object.equals("0")) {
                    attackers[0] = 0;
                } else {
                    attackers[0] = (int)slider.getValue();
                }
                System.out.println(attackers[0]);
            }
        };

        // add labels saying the max number of attackers and how many defenders there are
        dialog.text(new Label("Max attackers: " + maxAttackers, DialogFactory.skin)).padLeft(20).padRight(20).align(Align.left);
        dialog.text(new Label("Defenders: " + defenders, DialogFactory.skin)).padLeft(20).padRight(20).align(Align.right);

        dialog.getContentTable().row();

        // add slider and label showing number of units selected
        dialog.getContentTable().add(slider).padLeft(20).padRight(20).align(Align.left).expandX();
        dialog.getContentTable().add(sliderValue).padLeft(20).padRight(20).align(Align.right);

        dialog.getContentTable().row();

        // add buttons for accepting or canceling the selection
        dialog.button("Cancel", "0").padLeft(20).padRight(40).align(Align.center);
        dialog.button("Ok", "1").padLeft(40).padRight(20).align(Align.center);

        dialog.show(stage);
    }
}
