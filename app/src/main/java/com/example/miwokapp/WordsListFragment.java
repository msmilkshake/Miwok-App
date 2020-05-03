package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioFocusRequestCompat;
import androidx.media.AudioManagerCompat;

import java.util.ArrayList;
import java.util.List;

public class WordsListFragment extends Fragment {
    
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioFocusRequestCompat mAudioFocusRequest;
    
    private Bundle mBundle;
    private List<Word> mWords;
    
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    
    public WordsListFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list_fragment, container, false);
        
        mBundle = getArguments();
    
        mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releasePlayer();
            }
        };
    
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    
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
    
        mWords = populateWordsList();
        int color = mBundle.getInt("color");
        
        WordAdapter adapter = new WordAdapter(getActivity(), mWords, color);
    
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
    
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            
                releasePlayer();
            
                int result = AudioManagerCompat.requestAudioFocus(
                        mAudioManager,
                        mAudioFocusRequest);
            
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                
                    mMediaPlayer = MediaPlayer.create(
                            WordsListFragment.this.getActivity(),
                            mWords.get(position).getAudioID());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                
                }
            }
        });
        
        return rootView;
    }
    
    @Override
    public void onStop() {
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
    
    private List<Word> populateWordsList() {
        List<Word> words = new ArrayList<>();
        
        switch (mBundle.getInt("color")) {
            
            case R.color.category_numbers:
                words.add(new Word(getString(R.string.miwok_one),
                        getString(R.string.default_one),
                        R.drawable.number_one, R.raw.number_one));
                words.add(new Word(getString(R.string.miwok_two),
                        getString(R.string.default_two),
                        R.drawable.number_two, R.raw.number_two));
                words.add(new Word(getString(R.string.miwok_three),
                        getString(R.string.default_three),
                        R.drawable.number_three, R.raw.number_three));
                words.add(new Word(getString(R.string.miwok_four),
                        getString(R.string.default_four),
                        R.drawable.number_four, R.raw.number_four));
                words.add(new Word(getString(R.string.miwok_five),
                        getString(R.string.default_five),
                        R.drawable.number_five, R.raw.number_five));
                words.add(new Word(getString(R.string.miwok_six),
                        getString(R.string.default_six), R.drawable.number_six,
                        R.raw.number_six));
                words.add(new Word(getString(R.string.miwok_seven),
                        getString(R.string.default_seven),
                        R.drawable.number_seven, R.raw.number_seven));
                words.add(new Word(getString(R.string.miwok_eight),
                        getString(R.string.default_eight),
                        R.drawable.number_eight, R.raw.number_eight));
                words.add(new Word(getString(R.string.miwok_nine),
                        getString(R.string.default_nine),
                        R.drawable.number_nine, R.raw.number_nine));
                words.add(new Word(getString(R.string.miwok_ten),
                        getString(R.string.default_ten),
                        R.drawable.number_ten, R.raw.number_ten));
                break;
                
            case R.color.category_family:
                words.add(new Word(getString(R.string.miwok_father),
                        getString(R.string.default_father),
                        R.drawable.family_father, R.raw.family_father));
                words.add(new Word(getString(R.string.miwok_mother),
                        getString(R.string.default_mother),
                        R.drawable.family_mother, R.raw.family_mother));
                words.add(new Word(getString(R.string.miwok_son),
                        getString(R.string.default_son),
                        R.drawable.family_son, R.raw.family_son));
                words.add(new Word(getString(R.string.miwok_daughter),
                        getString(R.string.default_daughter),
                        R.drawable.family_daughter, R.raw.family_daughter));
                words.add(new Word(getString(R.string.miwok_older_brother),
                        getString(R.string.default_older_brother),
                        R.drawable.family_older_brother, R.raw.family_older_brother));
                words.add(new Word(getString(R.string.miwok_younger_brother),
                        getString(R.string.default_younger_brother),
                        R.drawable.family_younger_brother, R.raw.family_younger_brother));
                words.add(new Word(getString(R.string.miwok_older_sister),
                        getString(R.string.default_older_sister),
                        R.drawable.family_older_sister, R.raw.family_older_sister));
                words.add(new Word(getString(R.string.miwok_younger_sister),
                        getString(R.string.default_younger_sister),
                        R.drawable.family_younger_sister, R.raw.family_younger_sister));
                words.add(new Word(getString(R.string.miwok_grandmother),
                        getString(R.string.default_grandmother),
                        R.drawable.family_grandmother, R.raw.family_grandmother));
                words.add(new Word(getString(R.string.miwok_grandfather),
                        getString(R.string.default_grandfather),
                        R.drawable.family_grandfather, R.raw.family_grandfather));
                break;
                
            case R.color.category_colors:
                words.add(new Word(getString(R.string.miwok_red),
                        getString(R.string.default_red),
                        R.drawable.color_red, R.raw.color_red));
                words.add(new Word(getString(R.string.miwok_green),
                        getString(R.string.default_green),
                        R.drawable.color_green, R.raw.color_green));
                words.add(new Word(getString(R.string.miwok_brown),
                        getString(R.string.default_brown),
                        R.drawable.color_brown, R.raw.color_brown));
                words.add(new Word(getString(R.string.miwok_gray),
                        getString(R.string.default_gray),
                        R.drawable.color_gray, R.raw.color_gray));
                words.add(new Word(getString(R.string.miwok_black),
                        getString(R.string.default_black),
                        R.drawable.color_black, R.raw.color_black));
                words.add(new Word(getString(R.string.miwok_white),
                        getString(R.string.default_white),
                        R.drawable.color_white, R.raw.color_white));
                words.add(new Word(getString(R.string.miwok_dusty_yellow),
                        getString(R.string.default_dusty_yellow),
                        R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
                words.add(new Word(getString(R.string.miwok_mustard_yellow),
                        getString(R.string.default_mustard_yellow),
                        R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
                break;
                
            case R.color.category_phrases:
                words.add(new Word(getString(R.string.miwok_phrase_1),
                        getString(R.string.default_phrase_1),
                        R.raw.phrase_where_are_you_going));
                words.add(new Word(getString(R.string.miwok_phrase_2),
                        getString(R.string.default_phrase_2),
                        R.raw.phrase_what_is_your_name));
                words.add(new Word(getString(R.string.miwok_phrase_3),
                        getString(R.string.default_phrase_3),
                        R.raw.phrase_my_name_is));
                words.add(new Word(getString(R.string.miwok_phrase_4),
                        getString(R.string.default_phrase_4),
                        R.raw.phrase_how_are_you_feeling));
                words.add(new Word(getString(R.string.miwok_phrase_5),
                        getString(R.string.default_phrase_5),
                        R.raw.phrase_im_feeling_good));
                words.add(new Word(getString(R.string.miwok_phrase_6),
                        getString(R.string.default_phrase_6),
                        R.raw.phrase_are_you_coming));
                words.add(new Word(getString(R.string.miwok_phrase_7),
                        getString(R.string.default_phrase_7),
                        R.raw.phrase_yes_im_coming));
                words.add(new Word(getString(R.string.miwok_phrase_8),
                        getString(R.string.default_phrase_8),
                        R.raw.phrase_im_coming));
                words.add(new Word(getString(R.string.miwok_phrase_9),
                        getString(R.string.default_phrase_9),
                        R.raw.phrase_lets_go));
                words.add(new Word(getString(R.string.miwok_phrase_10),
                        getString(R.string.default_phrase_10),
                        R.raw.phrase_come_here));
                break;
                
            default:
        }
        return words;
    }
    
}