package com.hirarki.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //Memakai library Butter Knife
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private FloatingActionButton buttonAddTask;

    //Membuat class TabTodoAdapter lalu dibuat objek nya disini
    TabTodoAdapter tabTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        buttonAddTask = findViewById(R.id.floating_button_tambah);

        setStatusBarColor("#b96af1");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.warnaKhusus)));
        setupTabLayout();

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambah = new Intent(MainActivity.this, TambahJadwal.class);
                startActivity(tambah);
            }
        });
    }

    //Pengaturan warna di StatusBar
    public void setStatusBarColor(String color){
        Window window = getWindow();
        int statusBarColor = Color.parseColor(color);
        if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK){
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        window.setStatusBarColor(statusBarColor);
    }

    //Pengaturan warna pada setiap TabLayout
    private void setupTabLayout(){
        final ActionBar actionBar = getSupportActionBar();
        tabLayout.addTab(tabLayout.newTab().setText("KHUSUS"));
        tabLayout.addTab(tabLayout.newTab().setText("HARIAN"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabTodoAdapter = new TabTodoAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabTodoAdapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        setStatusBarColor("#b96af1");
                        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.warnaKhusus)));
                        tabLayout.setBackgroundColor(Color.parseColor("#b96af1"));
                        break;
                    case 1:
                        setStatusBarColor("#ea7f38");
                        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.warnaHarian)));
                        tabLayout.setBackgroundColor(Color.parseColor("#ea7f38"));
                        break;
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }
}
