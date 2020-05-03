package com.example.miwokapp;

public class Word {
    private String miwokWord;
    private String defaultWord;
    private int imageID = NO_IMAGE;
    private int audioID = NO_AUDIO;
    
    public static final int NO_IMAGE = -1;
    public static final int NO_AUDIO = -1;
    
    public Word(String miwokWord, String defaultWord, int imageID, int audioID) {
        this.miwokWord = miwokWord;
        this.defaultWord = defaultWord;
        this.imageID = imageID;
        this.audioID = audioID;
    }

    
    public Word(String miwokWord, String defaultWord, int audioID) {
        this(miwokWord, defaultWord, NO_IMAGE, audioID);
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public String getDefaultWord() {
        return defaultWord;
    }
    
    public int getImageID() {
        return imageID;
    }
    
    public int getAudioID() {
        return audioID;
    }
    
    public boolean hasImage() {
        return imageID != NO_IMAGE;
    }
    
}
