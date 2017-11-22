package sepr.game;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class Sector {
    private int id;
    private int ownerId;
    private int unitsInSector;
    private int reinforcementsProvided;
    private int[] adjacentSectorIds;

    public Sector(int id, int ownerId, int unitsInSector, int reinforcementsProvided, int[] adjacentSectorIds) {
        this.id = id;
        this.ownerId = ownerId;
        this.unitsInSector = unitsInSector;
        this.reinforcementsProvided = reinforcementsProvided;
        this.adjacentSectorIds = adjacentSectorIds;
    }

    /**
     * Changes the number of units in this sector
     * If there are 0 units in sector then ownerId should be -1 (neutral)
     * @throws IllegalArgumentException if units in sector is below 0
     * @param amount number of units to change by, (can be negative to subtract units
     */
    public void addUnits(int amount) {
        this.unitsInSector += amount;
    }
}
