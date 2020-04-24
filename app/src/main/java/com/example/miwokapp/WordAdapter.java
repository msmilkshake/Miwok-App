package com.example.miwokapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    
    private int colorID;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorID) {
        super(context, 0, words);
        this.colorID = colorID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View listItemView, @NonNull ViewGroup parent) {
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        View layout = listItemView.findViewById(R.id.text_container);
        layout.setBackgroundColor(ContextCompat.getColor(getContext(), colorID));
        Word word = getItem(position);
        ImageView image = listItemView.findViewById(R.id.list_image);
        if (word.hasImage()) {
            image.setImageResource(word.getImageID());
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }
        TextView miwokWord = listItemView.findViewById(R.id.miwok_word);
        miwokWord.setText(word.getMiwokWord());
        TextView defaultWord = listItemView.findViewById(R.id.default_word);
        defaultWord.setText(word.getDefaultWord());
        return listItemView;
    }
}
