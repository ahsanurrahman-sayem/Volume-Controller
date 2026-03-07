package com.junior.MyVolume;
import android.content.Context;
import android.media.AudioManager;

 public abstract class ArsAudioManager{
    static void showVolumeSlider(Context context){
		 AudioManager audioManager =
                    (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_SAME,
                    AudioManager.FLAG_SHOW_UI
            );
	 }
 }