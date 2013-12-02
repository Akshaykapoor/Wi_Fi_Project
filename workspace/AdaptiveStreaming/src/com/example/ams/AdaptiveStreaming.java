package com.example.ams;

import android.view.Menu;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import player.StreamingMediaPlayer;

public class AdaptiveStreaming extends Activity {

	private Button streamButton;
	
	private ImageButton playButton;
	
	private TextView textStreamed;
	
	private boolean isPlaying;
	
	private int calculatedBandwidth;
	
	private static final int LOW = 150;
	
	private static final int MEDIUM = 400;
	
	private static final int HIGH = 600;
	
	private StreamingMediaPlayer audioStreamer;
	
    @Override
	public void onCreate(Bundle icicle) {
    	
        super.onCreate(icicle);

        setContentView(R.layout.activity_main);
        initControls();
    } 
    
    private void initControls() {
    	textStreamed = (TextView) findViewById(R.id.text_kb_streamed);
		streamButton = (Button) findViewById(R.id.button_stream);
		streamButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startStreamingAudio();
        }});

		playButton = (ImageButton) findViewById(R.id.button_play);
		playButton.setEnabled(false);
		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (audioStreamer.getMediaPlayer().isPlaying()) {
					audioStreamer.getMediaPlayer().pause();
					playButton.setImageResource(R.drawable.button_play);
				} else {
					audioStreamer.getMediaPlayer().start();
					audioStreamer.startPlayProgressUpdater();
					playButton.setImageResource(R.drawable.button_pause);
				}
				isPlaying = !isPlaying;
        }});
    }
    
    private void startStreamingAudio() {
    	try { 
    		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    		if ( audioStreamer != null) {
    			audioStreamer.interrupt();
    		}
    		
    		calculatedBandwidth = 450;
    		if(calculatedBandwidth <= LOW)
    		{
    			audioStreamer = new StreamingMediaPlayer(this, textStreamed, playButton, streamButton, progressBar);
    			audioStreamer.startStreaming("http://www.cs.rit.edu/~hwb1551/sample1.mp3", 8704, 266);
    			streamButton.setEnabled(false);
    		}
    		
    		else if(LOW < calculatedBandwidth && calculatedBandwidth <= MEDIUM)
    		{
    			audioStreamer = new StreamingMediaPlayer(this, textStreamed, playButton, streamButton, progressBar);
    			audioStreamer.startStreaming("http://www.cs.rit.edu/~hwb1551/sample2.mp3", 10854, 330);
    			streamButton.setEnabled(false);
    		}
    		
    		else
    		{
    		audioStreamer = new StreamingMediaPlayer(this,textStreamed, playButton, streamButton,progressBar);
    		audioStreamer.startStreaming("http://www.cs.rit.edu/~hwb1551/sample3.mp3",6656, 10);
    		streamButton.setEnabled(false);
    		}
    	} catch (IOException e) {
	    	Log.e(getClass().getName(), "Error starting to stream audio.", e);            		
    	}
    	    	
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

