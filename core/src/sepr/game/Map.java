package sepr.game;

import java.util.HashMap;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class Map {
    private HashMap<Integer, Sector> sectors; // mapping of sector ID to the sector object

    /**
     * Checks to see if there is one player who controls every sector
     * @return -1 if there is no winner or the ID of the player that controlls all the sectors
     */
    private int checkForWinner() {
        return -1;
    }

    /**
     * Tranfers units from one sector to another
     * @throws IllegalArgumentException if the sector are not both owned by the same player
     * @throws IllegalArgumentException if the amount exceeds the number of units on the source sector
     * @throws IllegalArgumentException if the sectors are not connected
     * @param sourceSectorId where to move the units from
     * @param targetSectorId where to move the units to
     * @param amount how many units to move
     */
    private void moveUnits(int sourceSectorId, int targetSectorId, int amount) {

    }

    /**
     * calculates how many reinforcements the given player should receive based on the sectors they control
     * @param playerId player who calculation is for
     * @return returns the amount of reinforcements the player should be allocated
     */
    private int calculateReinforcementAmount(int playerId) {

        return 0;
    }

}
