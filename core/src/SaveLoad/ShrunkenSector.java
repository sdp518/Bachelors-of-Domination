package SaveLoad;

import sepr.game.Sector;

/**
 * This is class is the same as the ordinary Sector class in sepr.game.
 * The only difference is to make the class serializable.
 * It does this by removing all of the unnecessary information about the sectors.
 * This information can be reinputed from starting the game and then overwriting the relevant information
 * with the information in this class.
 */
public class ShrunkenSector implements java.io.Serializable {
    private int ownerId;
    private String displayName;
    private int unitsInSector;
    private int reinforcementsProvided;
    private String college; // name of the college this sector belongs to
    private boolean neutral; // is this sector a default neutral sector

    /**
     * This class takes the six most important variables and stores them to be saved.
     * @param sector The ordinary sector which will be trimmed to get the necessary information.
     */
    public ShrunkenSector(Sector sector) {
        this.ownerId = sector.getOwnerId();
        this.displayName = sector.getDisplayName();
        this.unitsInSector = sector.getUnitsInSector();
        this.reinforcementsProvided = sector.getReinforcementsProvided();
        this.college = sector.getCollege();
        this.neutral = sector.isNeutral();
    }

    /**
     *
     * @return the integer of the owning players ID.
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @return the String name of the sector.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @return the integer of the units which are present in the sector.
     */
    public int getUnitsInSector() {
        return this.unitsInSector;
    }

    /**
     *
     * @return the integer of the units to be added to the sector.
     */
    public int getReinforcementsProvided() {
        return reinforcementsProvided;
    }

    /**
     *
     * @return the String of the owning college.
     */
    public String getCollege() {
        return college;
    }

    /**
     *
     * @return the boolean of if the sector is neutral or not.
     */
    public boolean isNeutral() {
        return neutral;
    }
}
