package SaveLoad;

import com.badlogic.gdx.graphics.Color;
import sepr.game.GameSetupScreen;
import sepr.game.Player;
import sepr.game.PlayerType;

public class SavePlayer implements java.io.Serializable {
    private int id; // player's unique id
    private GameSetupScreen.CollegeName collegeName; // college this player chose
    private String playerName;
    private int troopsToAllocate; // how many troops the player has to allocate at the start of their next reinforcement phase
    private float[] sectorColour; // what colour to shade sectors owned by the player
    private PlayerType playerType; // Human or Neutral player

    public SavePlayer(Player player) {
        this.id = player.getId();
        this.collegeName = player.getCollegeName();
        this.playerName = player.getPlayerName();
        this.troopsToAllocate = player.getTroopsToAllocate();
        this.sectorColour = new float[] {player.getSectorColour().r,
                                        player.getSectorColour().g,
                                        player.getSectorColour().b,
                                        player.getSectorColour().a};
        this.playerType = player.getPlayerType();
    }

    public int getId() {
        return id;
    }

    public GameSetupScreen.CollegeName getCollegeName() {
        return this.collegeName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getTroopsToAllocate() {
        return troopsToAllocate;
    }

    public Color getSectorColor() {
        return new Color(sectorColour[0], sectorColour[1],
                        sectorColour[2], sectorColour[3]);
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
