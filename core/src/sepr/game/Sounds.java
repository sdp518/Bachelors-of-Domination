package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/** Deals with all audio **/
class Sounds {
    private Sound menuSound;
    private Sound attackSound;
    private Sound reinforceSound;
    private Sound moveSound;
    private Sound slotMachineLaunch;
    private Sound match2;
    private Sound slotMachineSpin;
    private float fxVolume;
    private Music music;
    private float musicVolume;

    /** Constructor for Class.
     * Sets up sounds and music
     * Gets and sets volume for sounds and music
     */
    public Sounds() {
        Preferences prefs = Gdx.app.getPreferences(OptionsScreen.PREFERENCES_NAME);
        this.menuSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu_sound.mp3"));
        this.attackSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/attack_sound.mp3"));
        this.reinforceSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/reinforce_sound.WAV"));
        this.moveSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/move_sound.WAV"));
        this.slotMachineLaunch = Gdx.audio.newSound(Gdx.files.internal("Sounds/slot_machine_launch.WAV"));
        this.match2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/match_2.WAV"));
        this.slotMachineSpin = Gdx.audio.newSound(Gdx.files.internal("Sounds/slot_machine_spin.WAV"));
        setFxVolume(prefs.getFloat(OptionsScreen.FX_VOL_PREF));
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/bensound-epic.mp3"));
        setMusicVolume(prefs.getFloat(OptionsScreen.MUSIC_VOL_PREF));
        this.music.setLooping(true);
        this.music.play();
    }

    /**
     * Plays the sound with the passed name
     * @param soundToPlay The name of the sound to play, for clarity this this corresponds to the file name
     */
    public void playSound(String soundToPlay){
        if (soundToPlay.equals("menu_sound")){
            menuSound.play(fxVolume);
        }
        else if (soundToPlay.equals("attack_sound")){
            attackSound.play(fxVolume);
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
            slotMachineSpin.loop();
            slotMachineSpin.play(fxVolume);
        }

    }

    /**
     * Sets the volume for Sound FX
     * @param volume the volume to set, should be a float between 0 and 1 where 1 is max volume
     *               and 0 is silent
     */
    public void setFxVolume(float volume){
        this.fxVolume = volume;
    }

    /**
     * Sets the volume for music
     * @param volume the volume to set, should be a float between 0 and 1 where 1 is max volume
     *               and 0 is silent
     */
    public void setMusicVolume(float volume){
        this.musicVolume = volume;
        music.setVolume(this.musicVolume);
    }

    public void stopLooping() {
        slotMachineSpin.stop();
    }
}
