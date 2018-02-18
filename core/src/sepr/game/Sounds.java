package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.Random;

/** NEW CLASS Deals with all audio **/
class Sounds {
    private Sound menuSound; // variables storing sounds used in the game
    private Sound attackSound;
    private Sound reinforceSound;
    private Sound moveSound;
    private Sound slotMachineLaunch;
    private Sound match2;
    private Music slotMachineSpin; // implemented as music since it is a long sound but is actually a sound effect
    private Sound slotMachineFail;
    private Sound match3;
    private Sound slotMachineGeese;
    private Sound attackSound2;
    private Sound winSound;
    private Sound pvcSound;
    private Sound playerEliminated;
    private float fxVolume; // the volume of the sound effects
    private Music music; // variable storing the music
    private float musicVolume; // the volume of the music

    /** NEW
     * Constructor for Class.
     * Sets up sounds and music
     * Gets and sets volume for sounds and music
     */
    public Sounds() {
        Preferences prefs = Gdx.app.getPreferences(OptionsScreen.PREFERENCES_NAME);
        this.menuSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu_sound.mp3"));
        this.attackSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/attack_sound.WAV"));
        this.reinforceSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/reinforce_sound.WAV"));
        this.moveSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/move_sound.WAV"));
        this.slotMachineLaunch = Gdx.audio.newSound(Gdx.files.internal("Sounds/slot_machine_launch.WAV"));
        this.match2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/match_2.WAV"));
        this.slotMachineSpin = Gdx.audio.newMusic(Gdx.files.internal("Sounds/slot_machine_spin.WAV"));
        this.slotMachineFail = Gdx.audio.newSound(Gdx.files.internal("Sounds/slot_machine_fail.WAV"));
        this.match3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/match_3.WAV"));
        this.slotMachineGeese = Gdx.audio.newSound(Gdx.files.internal("Sounds/slot_machine_geese.WAV"));
        this.winSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/win_sound.WAV"));
        this.attackSound2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/attack_sound2.WAV"));
        this.pvcSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/pvc_sound.WAV"));
        this.playerEliminated = Gdx.audio.newSound(Gdx.files.internal("Sounds/player_eliminated.WAV"));
        setFxVolume(prefs.getFloat(OptionsScreen.FX_VOL_PREF));
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/bensound-epic.mp3"));
        setMusicVolume(prefs.getFloat(OptionsScreen.MUSIC_VOL_PREF));
        this.music.setLooping(true);
        this.music.play();
    }

    /**
     * NEW
     * Plays the sound with the passed name
     * @param soundToPlay The name of the sound to play, for clarity this this corresponds to the file name
     */
    public void playSound(String soundToPlay){
        if (soundToPlay.equals("menu_sound")){
            menuSound.play(fxVolume);
        }
        else if (soundToPlay.equals("attack_sound")){ // adds 2% proc chance for "secret" attack sound
            Random rand = new Random();
            int chance = rand.nextInt(50);
            if (chance == 10){
                attackSound2.play(fxVolume);
            }
            else {
                attackSound.play(fxVolume);
            }
        }
        else if (soundToPlay.equals("reinforce_sound")){
            reinforceSound.play(fxVolume);
        }
        else if (soundToPlay.equals("move_sound")){
            moveSound.play(fxVolume);
        }
        else if (soundToPlay.equals("slot_machine_launch")){
            slotMachineLaunch.play(fxVolume);
        }
        else if (soundToPlay.equals("match_2")) {
            match2.play(fxVolume);
        }
        else if (soundToPlay.equals("slot_machine_spin")) {
            slotMachineSpin.setVolume(fxVolume);
            slotMachineSpin.play();
        }
        else if (soundToPlay.equals("slot_machine_fail")) {
            slotMachineFail.play(fxVolume);
        }
        else if (soundToPlay.equals("match_3")) {
            match3.play(fxVolume);
        }
        else if (soundToPlay.equals("slot_machine_geese")) {
            slotMachineGeese.play(fxVolume);
        }
        else if (soundToPlay.equals("win_sound")) {
            winSound.play(fxVolume);
        }
        else if (soundToPlay.equals("pvc_sound")) {
            pvcSound.play(fxVolume);
        }
        else if (soundToPlay.equals("player_eliminated")) {
            playerEliminated.play(fxVolume);
        }

    }

    /**
     * NEW
     * Sets the volume for Sound FX
     * @param volume the volume to set, should be a float between 0 and 1 where 1 is max volume
     *               and 0 is silent
     */
    public void setFxVolume(float volume){
        this.fxVolume = volume;
    }

    /**
     * NEW
     * Sets the volume for music
     * @param volume the volume to set, should be a float between 0 and 1 where 1 is max volume
     *               and 0 is silent
     */
    public void setMusicVolume(float volume){
        this.musicVolume = volume;
        music.setVolume(this.musicVolume);
    }

    /**
     * NEW
     * Stops the slot machine spinning sound when a result is produced
     */
    public void stopLooping() {
        slotMachineSpin.stop();
    }
}
