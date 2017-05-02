package com.like26th.likeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindArray(R.array.main_item)
    String[] items;

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MainAdapter(this, items));
    }
}
