package com.engramtd.tdinh.echo;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String filePath;
    private MediaRecorder aRecorder;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filePath = getFilesDir() + "/rd.aac";

        final ToggleButton recordButton = (ToggleButton) findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AudioManager aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                aManager.get
                if (mPlayer != null) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                }

                if (aRecorder == null){
                    aRecorder = new MediaRecorder();
                    aRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    aRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    aRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    try {
                        aRecorder.setOutputFile(filePath);
                        aRecorder.prepare();
                        aRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    aRecorder.stop();
                    aRecorder.release();
                    aRecorder = null;
                    // Play the recorded audio.

                    mPlayer = new MediaPlayer();
                    try {
                        mPlayer.setDataSource(filePath);
//                    LoudnessEnhancer ampEffect = new LoudnessEnhancer(mPlayer.getAudioSessionId());
//                    ampEffect.setTargetGain(50*100);
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(aRecorder != null) {
            aRecorder.stop();
            aRecorder.release();
            aRecorder = null;
        }
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
