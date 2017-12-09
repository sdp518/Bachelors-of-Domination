package sepr.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class Sector {
    private int id;
    private int ownerId;
    private int prevOwnerId;
    private String displayName;
    private int unitsInSector;
    private int reinforcementsProvided;
    private int[] adjacentSectorIds; // <-- May want to reconsider structure
    private Texture sectorTexture;
    private Pixmap sectorPixmap;
    private int sectorCentreX;
    private int sectorCentreY;
    private boolean decor; // is this sector for visual purposes only, i.e. lakes are decor
    private String fileName;

    public Sector(int id, int ownerId, String displayName, int unitsInSector, int reinforcementsProvided, int[] adjacentSectorIds, Texture sectorTexture, Pixmap sectorPixmap, String fileName, int sectorCentreX, int sectorCentreY, boolean decor) {
        this.id = id;
        this.ownerId = ownerId;
        this.prevOwnerId = ownerId;
        this.displayName = displayName;
        this.unitsInSector = unitsInSector;
        this.reinforcementsProvided = reinforcementsProvided;
        this.adjacentSectorIds = adjacentSectorIds;
        this.sectorTexture = sectorTexture;
        this.sectorPixmap = sectorPixmap;
        this.sectorCentreX = sectorCentreX;
        this.sectorCentreY = sectorCentreY;
        this.decor = decor;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public int getPrevOwnerId() { return prevOwnerId; }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getReinforcementsProvided() {
        return reinforcementsProvided;
    }

    public int getUnitsInSector() {
        return unitsInSector;
    }

    public int[] getAdjacentSectorIds() {
        return adjacentSectorIds;
    }

    public Texture getSectorTexture() {
        return sectorTexture;
    }

    public void setNewSectorTexture(Pixmap newPixmap) {
        this.sectorTexture.dispose();
        Texture temp = new Texture(newPixmap);
        this.sectorTexture = temp;
    }

    public Pixmap getSectorPixmap() {
        return sectorPixmap;
    }
  
    public int getSectorCentreX() {
        return sectorCentreX;
    }

    public int getSectorCentreY() {
        return sectorCentreY;
    }

    public boolean isDecor() {
        return decor;
    }

    public String getFileName() {
        return fileName;
    }

    public void updateOwnerId() { prevOwnerId = ownerId; }

    /**
     * Changes the number of units in this sector
     * If there are 0 units in sector then ownerId should be -1 (neutral)
     * @throws IllegalArgumentException if units in sector is below 0
     * @param amount number of units to change by, (can be negative to subtract units
     */
    public void addUnits(int amount) {
        this.unitsInSector += amount;
    }

    public boolean justCapturedBy(int playerId){ return playerId == ownerId && playerId != prevOwnerId;  }

}
