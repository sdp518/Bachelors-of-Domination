package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

class Sounds {
    private Sound menuSound;
    private Sound attackSound;
    private float fxVolume;
    private Music music;
    private float musicVolume;

    public Sounds() {
        Preferences prefs = Gdx.app.getPreferences(OptionsScreen.PREFERENCES_NAME);
        this.menuSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu_sound.mp3"));
        this.fxVolume = prefs.getFloat(OptionsScreen.FX_VOL_PREF);
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/bensound-epic.mp3"));
        this.music.setLooping(true);
        this.music.play();
    }

    public void playSound(String soundToPlay){
        if (soundToPlay.equals("menu_sound")){
            menuSound.play(fxVolume);
        }
        else if (soundToPlay .equals("attack_sound")){
            attackSound.play(fxVolume);
        }

    }

    public void setFxVolume(float volume){
        this.fxVolume = volume;
    }

    public void setMusicVolume(float volume){
        this.musicVolume = volume;
        music.setVolume(this.musicVolume);
    }
}
