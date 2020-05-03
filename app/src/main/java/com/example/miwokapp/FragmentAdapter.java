package com.example.miwokapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    
    private String pageTitles[] = {"Numbers", "Family", "Colors", "Phrases"};
    
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        
        switch (position) {
            case 0:
                bundle.putInt("color", R.color.category_numbers);
                break;
            case 1:
                bundle.putInt("color", R.color.category_family);
                break;
            case 2:
                bundle.putInt("color", R.color.category_colors);
                break;
            case 3:
                bundle.putInt("color", R.color.category_phrases);
                break;
            default:
        }
        
        WordsListFragment fragment = new WordsListFragment();
        fragment.setArguments(bundle);
        
        return fragment;
    }
    
    @Override
    public int getCount() {
        return 4;
    }
    
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
