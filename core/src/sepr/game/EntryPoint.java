package sepr.game;

/**
 * NEW CLASS
 * Can be passed to screens to show where that screen was accessed from and where it
 * should return to
 */
public enum EntryPoint {
    GAME_SCREEN("GAME SCREEN"),
    MENU_SCREEN("HUMAN PLAYER");

    private final String shortCode;

    EntryPoint(String code){
        this.shortCode = code;
    }

    public String getEntryPoint(){
        return this.shortCode;
    }

    /**
     * converts the string representation of the enum to the enum value
     * @throws IllegalArgumentException if the text does not match any of the enum's string values
     * @param text string representation of the enum
     * @return the enum value of the provided text
     */
    public static EntryPoint fromString(String text) throws IllegalArgumentException {
        for (EntryPoint entryPoint : EntryPoint.values()) {
            if (entryPoint.getEntryPoint().equals(text)) return entryPoint;
        }
        throw new IllegalArgumentException("Text parameter must match one of the enums");
    }
}
