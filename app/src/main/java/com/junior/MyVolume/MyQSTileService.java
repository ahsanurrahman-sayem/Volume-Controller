package com.junior.MyVolume;

import android.content.Context;
import android.media.AudioManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

public class MyQSTileService extends TileService {
	AudioManager audioManager;

	private final String TAG = MyQSTileService.class.getSimpleName();
	private final int state_on = 1, sate_off = 0, toogle = state_on;

	// Called when the user adds your tile.
	@Override
	public void onTileAdded() {
		super.onTileAdded();
		Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
	}

	// Called when your app can update your tile.
	@Override
	public void onStartListening() {
		super.onStartListening();
		Tile tile = getQsTile();
	}

	// Called when your app can no longer update your tile.
	@Override
	public void onStopListening() {
		super.onStopListening();
	}

	// Called when the user taps on your tile in an active or inactive state.
	@Override
	public void onClick() {
		super.onClick();
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	//	int count = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		//count++;
	//	audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, count, AudioManager.ADJUST_SAME);
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,audioManager.ADJUST_SAME ,AudioManager.FLAG_SHOW_UI);
        Toast.makeText(getApplicationContext(),"Ui show",Toast.LENGTH_SHORT).show();
		getQsTile().updateTile();
	}

	// Called when the user removes your tile.
	@Override
	public void onTileRemoved() {
		super.onTileRemoved();
	}
}