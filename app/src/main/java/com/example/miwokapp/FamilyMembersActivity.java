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

public class FamilyMembersActivity extends AppCompatActivity {
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
        
        mWords.add(new Word(getString(R.string.miwok_father), getString(R.string.default_father), R.drawable.family_father, R.raw.family_father));
        mWords.add(new Word(getString(R.string.miwok_mother), getString(R.string.default_mother), R.drawable.family_mother, R.raw.family_mother));
        mWords.add(new Word(getString(R.string.miwok_son), getString(R.string.default_son), R.drawable.family_son, R.raw.family_son));
        mWords.add(new Word(getString(R.string.miwok_daughter), getString(R.string.default_daughter), R.drawable.family_daughter, R.raw.family_daughter));
        mWords.add(new Word(getString(R.string.miwok_older_brother), getString(R.string.default_older_brother), R.drawable.family_older_brother, R.raw.family_older_brother));
        mWords.add(new Word(getString(R.string.miwok_younger_brother), getString(R.string.default_younger_brother), R.drawable.family_younger_brother, R.raw.family_younger_brother));
        mWords.add(new Word(getString(R.string.miwok_older_sister), getString(R.string.default_older_sister), R.drawable.family_older_sister, R.raw.family_older_sister));
        mWords.add(new Word(getString(R.string.miwok_younger_sister), getString(R.string.default_younger_sister), R.drawable.family_younger_sister, R.raw.family_younger_sister));
        mWords.add(new Word(getString(R.string.miwok_grandmother), getString(R.string.default_grandmother), R.drawable.family_grandmother, R.raw.family_grandmother));
        mWords.add(new Word(getString(R.string.miwok_grandfather), getString(R.string.default_grandfather), R.drawable.family_grandfather, R.raw.family_grandfather));
        
        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_family);
        
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
        
                    mMediaPlayer = MediaPlayer.create(FamilyMembersActivity.this, mWords.get(position).getAudioID());
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