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
        	e.printStackTrace();
            Log.v("TAG", e.getMessage());
            return;
        }
        // Max will be song length.
        progress.setMax(mp.getDuration());
        progress.setProgress(0);


        // Set callbacks
        pausePlay.setOnClickListener(new MediaPlayerOnClickListener(mp));
        progress.setOnSeekBarChangeListener(new MediaPlayerUpdate(mp));

        // New thread to update the seekbar
        new Thread(new SeekBarUpdateThread(mp, progress)).start();
    }

    /**
     * Class for updating the mediaplayer based on the seekbar position.
     */
    private class MediaPlayerUpdate implements SeekBar.OnSeekBarChangeListener {
    	private MediaPlayer mediaPlayer;

    	public MediaPlayerUpdate(MediaPlayer mp){
    		mediaPlayer = mp;
    	}

    	@Override
    	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){}

    	/**
    	 * Pause mediaplayer when touching seekbar.
    	 */
    	@Override
    	public void onStartTrackingTouch(SeekBar seekBar){
    		mediaPlayer.pause();
    	}

    	/**
    	 * Update mediaplayer based on seekbar position.
    	 */
    	@Override
    	public void onStopTrackingTouch(SeekBar seekBar){
    		mediaPlayer.seekTo(seekBar.getProgress());
    		mediaPlayer.start();
    	}
    }

    /**
     * Class for asynchronously updating the seekbar based on the mediaplayer.
     */
    private class SeekBarUpdateThread implements Runnable {
    	private MediaPlayer mediaPlayer;
    	private SeekBar progress;

    	public SeekBarUpdateThread(MediaPlayer mp, SeekBar prg){
    		super();
    		mediaPlayer = mp;
    		progress = prg;
    	}

    	/**
    	 * Update seekbar based on time into mediaplayer.
    	 */
    	@Override
    	public void run(){
    		while (mp.getCurrentPosition() < mp.getDuration()){
    			progress.setProgress(mp.getCurrentPosition());
    			try {
	    			Thread.sleep(1000);
				} catch (Exception e){
					e.printStackTrace();
					Log.v("TAG", e.getMessage());
				}
    		}
    	}
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
    		super();
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