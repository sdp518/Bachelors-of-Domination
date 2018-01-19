package sepr.game;

import java.util.HashMap;

/**
 * stores the properties of a college
 */
public class College {

    private int id; // unique identifier for this college
    private int[] sectorIds; // ids of sectors contained within this college
    private int reinforcementAmount; // amount of bonus troops provided per round if all college sectors are held
    private String displayName; // name of college shown to players

    /**
     * initalises a new college object with the specified id; display name; reinforcement bonus and sectors within the college
     * @param id the college's unique id
     * @param displayName the colleges name, that will be displayed
     * @param reinforcementAmount the amount of troops provided to the player if they control all colleges in the sector
     * @param sectorIds sector ids of the sectors that belong to this college
     */
    public College(int id, String displayName, int reinforcementAmount, int[] sectorIds){
        this.id = id;
        this.displayName = displayName;
        this.sectorIds = sectorIds;
        this.reinforcementAmount = reinforcementAmount;
    }

    /**
     *
     * @return the unique id of this college
     */
    public int getId() { return id; }

    /**
     * @return The amount of reinforcements provided by the college each turn
     */
    public int getReinforcementAmount() {
        return reinforcementAmount;
    }

    /**
     * @return all the ids within the sector
     */
    public int[] getSectorIds(){
        return sectorIds;
    }

    /**
     *
     * @return the name of the college to be shown to players
     */
    public String getDisplayName() { return displayName; }
}
