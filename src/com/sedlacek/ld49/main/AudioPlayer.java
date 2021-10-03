package com.sedlacek.ld49.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

    public Clip clip;

    public AudioPlayer(String filename){
        this.clip = loadClip(filename);
    }

    public Clip loadClip(String filename){
        Config.debug(filename);
        Clip clip = null;
        try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filename));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        }catch (Exception e){
            System.err.println("Couldn't load audio file! "+e.toString());
        }
        return clip;
    }

    public void playClip(){
        stopClip();
        clip.setFramePosition(0);
        clip.start();
    }

    public void resumeClip(){
        clip.start();
    }

    public void stopClip(){
        if(clip.isRunning())
            clip.stop();
    }

    public void loop(){
        if(clip.isRunning())
            clip.stop();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
