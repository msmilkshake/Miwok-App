package com.example.miwokapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Implementing OnCLickListener interface here.
//        TextView buttonsTextView = findViewById(R.id.numbers);
//        buttonsTextView.setOnClickListener(this);
//        TextView familyMembersTextView = findViewById(R.id.family);
//        familyMembersTextView.setOnClickListener(this);

//        Using the NumbersClickListener Object:
//        NumbersClickListener clickListener = new NumbersClickListener();
        TextView numbers = findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(i);
            }
        });
        TextView family = findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FamilyMembersActivity.class);
                startActivity(i);
            }
        });
        TextView colors = findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(i);
            }
        });
        TextView phrases = findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(i);
            }
        });
    }

    public void openNumbersList(View view) {
        Intent i = new Intent(this, NumbersActivity.class);
        startActivity(i);
    }

//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.numbers) {
//            openNumbersList(view);
//        }
//        Toast.makeText(this, "Click detected", Toast.LENGTH_SHORT).show();
//    }
}