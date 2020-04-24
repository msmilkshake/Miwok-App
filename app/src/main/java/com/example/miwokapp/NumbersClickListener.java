package com.example.miwokapp;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class NumbersClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Touch Test", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(v.getContext(), NumbersActivity.class);
        v.getContext().startActivity(i);
    }
}
