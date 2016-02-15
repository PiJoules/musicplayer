package com.example.something;

import android.app.Activity;
import android.util.Log;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.content.Intent;
import android.view.View;


public class SongActivity extends Activity {
	private Button pausePlay;
	private SeekBar progress;

	private MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song);

        // Get song path from intent
        Intent intent = getIntent();
        String songPath = intent.getStringExtra("songPath");

        // Set giu stuff
        pausePlay = (Button) findViewById(R.id.pausePlay);
        progress = (SeekBar) findViewById(R.id.progress);

        // Steup media player
        mp = new MediaPlayer();
        try {
	        mp.setDataSource(songPath);
	        mp.prepare();
            mp.start();
            pausePlay.setText("Pause");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.v("TAG", e.getMessage());
        }

        // Set button callbacks
        pausePlay.setOnClickListener(new MediaPlayerOnClickListener(mp));
    }

    /**
     * Kill the mediaplayer when leaving.
     */
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	mp.stop();
    }

    /**
     * Custom OnClickListener to be able to pass a meadia player.
     */
    private class MediaPlayerOnClickListener implements View.OnClickListener {
    	private MediaPlayer mediaPlayer;

    	public MediaPlayerOnClickListener(MediaPlayer mp){
    		mediaPlayer = mp;
    	}

        @Override
        public void onClick(View v) {
        	if (!mediaPlayer.isPlaying()){
        		// Was paused. Now play.
            	pausePlay.setText("Pause");
            	mediaPlayer.start();
            }
            else {
            	// Was playing. Now pause.
            	pausePlay.setText("Play");
            	mediaPlayer.pause();
            }
        }
    }
}