package sepr.game;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 *
 */
public class College {

    private int id;
    private List<Integer> sectorIds; // ids of sectors contained within this college
    private int reinforcementAmount; // amount of bonus troops provided per round if all college sectors are held
    private String displayName; // name of college shown to players

    /**
     *
     * @param id
     * @param displayName
     * @param reinforcementAmount
     * @param sectorIds
     */
    public College(int id, String displayName, int reinforcementAmount, List<Integer> sectorIds){
        this.id = id;
        this.displayName = displayName;
        this.sectorIds = sectorIds;
        this.reinforcementAmount = reinforcementAmount;
    }

    /**
     * @param reinforcementAmount the amount of reinforcements provided by the college each turn
     */
    public void setReinforcementAmount(int reinforcementAmount) { this.reinforcementAmount = reinforcementAmount; }

    /**
     * @param sectorId the sector you want to add to the college
     */
    public void addSectorId(int sectorId){
        this.sectorIds.add(sectorId);
    }
  
    /**
     * @return The amount of reinforcements provided by the college each turn
     */
    public int getReinforcementAmount() {
        return reinforcementAmount;
    }

    /**
     * @return all the ids within the sector
     */
    public List<Integer> getSectorIds(){
        return sectorIds;
    }

    /**
     * @param playerId id of the player to check
     * @param sectorsMap the hashmap containing all the sectors
     * @return true if all the sectors in the college are owned by the playerId else false
     */
    public boolean playerOwnsCollege(int playerId, HashMap<Integer, Sector> sectorsMap){
        for (int sId : sectorIds){
            if (sectorsMap.get(sId).getOwnerId() != playerId)
                return false;
        }
        return true;
    }

    /**
     *
     * @return the name of the college to be shown to players
     */
    public String getDisplayName() { return displayName; }
}
