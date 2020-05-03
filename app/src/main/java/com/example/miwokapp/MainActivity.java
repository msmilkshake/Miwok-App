package com.example.miwokapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        ViewPager viewPager = findViewById(R.id.viewpager_container);
    
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
    
        viewPager.setAdapter(adapter);
    
        TabLayout tabLayout = findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    
}