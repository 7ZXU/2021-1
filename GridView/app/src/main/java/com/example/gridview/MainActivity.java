package com.example.gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    int[] itemsarray = new int[]{
            R.drawable.bj1, R.drawable.bj2,
            R.drawable.bj3, R.drawable.bj4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.grid_view); //그리드뷰 불러오기

        //create object of myBaseAdapter
        MyBaseAdapter baseAdapter = new MyBaseAdapter(this, itemsarray);
        gridView.setAdapter(baseAdapter);
    }
}