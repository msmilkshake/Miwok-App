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

public class PhrasesActivity extends AppCompatActivity {
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
        
        mWords.add(new Word(getString(R.string.miwok_phrase_1), getString(R.string.default_phrase_1), Word.NO_IMAGE, R.raw.phrase_where_are_you_going));
        mWords.add(new Word(getString(R.string.miwok_phrase_2), getString(R.string.default_phrase_2), Word.NO_IMAGE, R.raw.phrase_what_is_your_name));
        mWords.add(new Word(getString(R.string.miwok_phrase_3), getString(R.string.default_phrase_3), Word.NO_IMAGE, R.raw.phrase_my_name_is));
        mWords.add(new Word(getString(R.string.miwok_phrase_4), getString(R.string.default_phrase_4), Word.NO_IMAGE, R.raw.phrase_how_are_you_feeling));
        mWords.add(new Word(getString(R.string.miwok_phrase_5), getString(R.string.default_phrase_5), Word.NO_IMAGE, R.raw.phrase_im_feeling_good));
        mWords.add(new Word(getString(R.string.miwok_phrase_6), getString(R.string.default_phrase_6), Word.NO_IMAGE, R.raw.phrase_are_you_coming));
        mWords.add(new Word(getString(R.string.miwok_phrase_7), getString(R.string.default_phrase_7), Word.NO_IMAGE, R.raw.phrase_yes_im_coming));
        mWords.add(new Word(getString(R.string.miwok_phrase_8), getString(R.string.default_phrase_8), Word.NO_IMAGE, R.raw.phrase_im_coming));
        mWords.add(new Word(getString(R.string.miwok_phrase_9), getString(R.string.default_phrase_9), Word.NO_IMAGE, R.raw.phrase_lets_go));
        mWords.add(new Word(getString(R.string.miwok_phrase_10), getString(R.string.default_phrase_10), Word.NO_IMAGE, R.raw.phrase_come_here));
        
        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_phrases);
        
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
    
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, mWords.get(position).getAudioID());
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