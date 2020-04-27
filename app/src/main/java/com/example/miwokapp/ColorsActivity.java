package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioFocusRequestCompat;
import androidx.media.AudioManagerCompat;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private ArrayList<Word> mWords;
    
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private AudioFocusRequestCompat mAudioFocusRequest;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);
        
        mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releasePlayer();
            }
        };
    
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    
        AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        mMediaPlayer.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releasePlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                        break;
                    default:
                }
            }
        };
    
        AudioAttributesCompat audioAttributes = new AudioAttributesCompat.Builder()
                .setContentType(AudioAttributesCompat.CONTENT_TYPE_SPEECH)
                .setUsage(AudioAttributesCompat.USAGE_MEDIA)
                .build();
        
        mAudioFocusRequest = new AudioFocusRequestCompat
                .Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build();
        
        mWords = new ArrayList<>();
        
        mWords.add(new Word(getString(R.string.miwok_red), getString(R.string.default_red), R.drawable.color_red, R.raw.color_red));
        mWords.add(new Word(getString(R.string.miwok_green), getString(R.string.default_green), R.drawable.color_green, R.raw.color_green));
        mWords.add(new Word(getString(R.string.miwok_brown), getString(R.string.default_brown), R.drawable.color_brown, R.raw.color_brown));
        mWords.add(new Word(getString(R.string.miwok_gray), getString(R.string.default_gray), R.drawable.color_gray, R.raw.color_gray));
        mWords.add(new Word(getString(R.string.miwok_black), getString(R.string.default_black), R.drawable.color_black, R.raw.color_black));
        mWords.add(new Word(getString(R.string.miwok_white), getString(R.string.default_white), R.drawable.color_white, R.raw.color_white));
        mWords.add(new Word(getString(R.string.miwok_dusty_yellow), getString(R.string.default_dusty_yellow), R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        mWords.add(new Word(getString(R.string.miwok_mustard_yellow), getString(R.string.default_mustard_yellow), R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        
        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_colors);
        
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                releasePlayer();
    
                int result = AudioManagerCompat.requestAudioFocus(
                        mAudioManager,
                        mAudioFocusRequest);
                
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, mWords.get(position).getAudioID());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        
                }
            }
        });
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.v("ColorsActivity",
                            "no ParentActivityMain in the android manifest.");
                    Toast.makeText(ColorsActivity.this,
                            "No parent activity",
                            Toast.LENGTH_SHORT)
                            .show();
                    return true;
                } else {
                    Toast.makeText(ColorsActivity.this,
                            "Success",
                            Toast.LENGTH_SHORT)
                            .show();
                    return super.onOptionsItemSelected(item);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void releasePlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    
        AudioManagerCompat.abandonAudioFocusRequest(
                mAudioManager,
                mAudioFocusRequest);
    }
}