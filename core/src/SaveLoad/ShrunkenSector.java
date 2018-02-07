package SaveLoad;

import sepr.game.Sector;

public class ShrunkenSector implements java.io.Serializable {
    private int ownerId;
    private String displayName;
    private int unitsInSector;
    private int reinforcementsProvided;
    private String college; // name of the college this sector belongs to
    private boolean neutral; // is this sector a default neutral sector

    public ShrunkenSector(Sector sector) {
        this.ownerId = sector.getOwnerId();
        this.displayName = sector.getDisplayName();
        this.unitsInSector = sector.getUnitsInSector();
        this.reinforcementsProvided = sector.getReinforcementsProvided();
        this.college = sector.getCollege();
        this.neutral = sector.isNeutral();
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getUnitsInSector() {
        return this.unitsInSector;
    }

    public int getReinforcementsProvided() {
        return reinforcementsProvided;
    }

    public String getCollege() {
        return college;
    }

    public boolean isNeutral() {
        return neutral;
    }
}
