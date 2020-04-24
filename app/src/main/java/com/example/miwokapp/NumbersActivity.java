package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioFocusRequestCompat;
import androidx.media.AudioManagerCompat;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioFocusRequestCompat mAudioFocusRequest;
    
    
    private ArrayList<Word> mWords;
    
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    
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
        
        mWords.add(new Word(getString(R.string.miwok_one), getString(R.string.default_one), R.drawable.number_one, R.raw.number_one));
        mWords.add(new Word(getString(R.string.miwok_two), getString(R.string.default_two), R.drawable.number_two, R.raw.number_two));
        mWords.add(new Word(getString(R.string.miwok_three), getString(R.string.default_three), R.drawable.number_three, R.raw.number_three));
        mWords.add(new Word(getString(R.string.miwok_four), getString(R.string.default_four), R.drawable.number_four, R.raw.number_four));
        mWords.add(new Word(getString(R.string.miwok_five), getString(R.string.default_five), R.drawable.number_five, R.raw.number_five));
        mWords.add(new Word(getString(R.string.miwok_six), getString(R.string.default_six), R.drawable.number_six, R.raw.number_six));
        mWords.add(new Word(getString(R.string.miwok_seven), getString(R.string.default_seven), R.drawable.number_seven, R.raw.number_seven));
        mWords.add(new Word(getString(R.string.miwok_eight), getString(R.string.default_eight), R.drawable.number_eight, R.raw.number_eight));
        mWords.add(new Word(getString(R.string.miwok_nine), getString(R.string.default_nine), R.drawable.number_nine, R.raw.number_nine));
        mWords.add(new Word(getString(R.string.miwok_ten), getString(R.string.default_ten), R.drawable.number_ten, R.raw.number_ten));
        
        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_numbers);
        
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
        
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, mWords.get(position).getAudioID());
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