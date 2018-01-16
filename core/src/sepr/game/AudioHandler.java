package sepr.game;

/**
 * class for controlling the audio being played
 */
public class AudioHandler {
    private static float MUSIC_PERCENTAGE = 1; // float value between 0 and 1 representing percentage amount of music volume
    private static float FX_PERCENTAGE = 1; // float value between 0 and 1 representing percentage amount of music volume


    /**
     * @throws IllegalArgumentException if volume is not between 0 and 1
     * @param musicPercentage volume of music as a value between 0 and 1
     */
    public static void setMusicPercentage(float musicPercentage) throws IllegalArgumentException {
        if (musicPercentage < 0 || musicPercentage > 1) throw new IllegalArgumentException("Music volume must be between 0 and 1");
        MUSIC_PERCENTAGE = musicPercentage;
    }

    /**
     * @throws IllegalArgumentException if volume is not between 0 and 1
     * @param fxPercentage volume of soundFX between 0 and 1
     */
    public static void setFxPercentage(float fxPercentage) throws IllegalArgumentException {
        if (fxPercentage < 0 || fxPercentage > 1) throw new IllegalArgumentException("SoundFX volume must be between 0 and 1");
        FX_PERCENTAGE = fxPercentage;
    }
}
