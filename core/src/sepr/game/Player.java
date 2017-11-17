package sepr.game;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public abstract class Player {
    private int id;
    private static Map map;
    private String collegeName;
    private int troopsToAllocate;

    /**
     *
     * @param id player's unique identifier
     * @param map map for the player to carry out actions upon
     * @param collegeName display name for this player
     */
    public Player(int id, Map map, String collegeName) {
        this.id = id;
        this.map = map;
        this.collegeName = collegeName;
        this.troopsToAllocate = 0;
    }


    /**
     * Let player attack enemy territories adjacent to its own
     */
    protected abstract void processAttackPhase();

    /**
     * Let player move troops between adjacent friendly territories
     */
    protected abstract void processMovePhase();

    /**
     * Let the player choose where to allocate their additional reinforcements
     * @param amount thr amount of toops available to allocate
     * @return how many troops remaining they may allocate in a future turn
     */
    protected abstract int processAllocationPhase(int amount);

    /**
     * resolves a conflict between two territories
     * @throws IllegalArgumentException if a player is attacking a sector it owns
     * @throws IllegalArgumentException if sectors are not connected
     * @param attackingSectorId id of sector carrying out the attack
     * @param defendingSectorId id of sector being attacked
     * @param amountOfTroopsAttacking number of troops that are being used to attack with
     */
    private static void resolveCombat(int attackingSectorId, int defendingSectorId, int amountOfTroopsAttacking) {

    }

}
