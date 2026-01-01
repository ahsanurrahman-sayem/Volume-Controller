package com.junior.MyVolume;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	SeekBar seekbar;
	TextView textView;
	AudioManager audioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		textView = findViewById(R.id.val);
		seekbar = findViewById(R.id.layout2SeekBar1);

		int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		seekbar.setMax(max);

		textView.setText("VOLUME: " + audioManager.getStreamVolume(
			AudioManager.STREAM_MUSIC) + " of " + max);

		seekbar.setProgress(audioManager.getStreamVolume(
			AudioManager.STREAM_MUSIC));

		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
				if (fromUser) {
					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						progress, AudioManager.FLAG_SHOW_UI);
					textView.setText("VOLUME: " + progress + " of " + max);
				}
			}
			@Override public void onStartTrackingTouch(SeekBar sb) {}
			@Override public void onStopTrackingTouch(SeekBar sb) {}
		});
	}

	@Override public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP) {
			int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
				volume = Math.min(volume + 1,
					audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
			} else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
				volume = Math.max(volume - 1, 0);
			}
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				volume, AudioManager.FLAG_SHOW_UI);
			seekbar.setProgress(volume);
			textView.setText("VOLUME: " + volume + " of " +
				audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		}
		return super.dispatchKeyEvent(event);
	}
}
	/*	CUSTOM FUNCTION SECTION	*/

	public void upVolume() {
		seekbar.setProgress(seekbar.getProgress() + 1);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekbar.getProgress(), AudioManager.ADJUST_SAME);
		audioManager.adjustVolume(seekbar.getProgress(), AudioManager.STREAM_MUSIC);
	}

	private int getCurrentVolume() {
		return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	private int getMaxVolume() {
		return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	private int getMinVolume() {
		return audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);
	}
	/*	CUSTOM FUNCTION SECTION END's	*/
}