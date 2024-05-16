package com.example.satgitsin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.satgitsin.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding connected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connected = ActivityMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(connected.getRoot());

        homeFrameShow();

        connected.bottomNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int objectId = menuItem.getItemId();

                if (objectId == R.id.menu_homepage)
                {
                    homeFrameShow();
                    return true;
                }
                else if(objectId == R.id.menu_messages)
                {
                    messageFrameShow();
                    return true;
                }
                else if(objectId == R.id.menu_sale)
                {
                    saleFrameShow();
                    return true;
                }
                else if(objectId == R.id.menu_favorite)
                {
                    favoriteFrameShow();
                    return true;
                }
                else if(objectId == R.id.menu_account)
                {
                    accountFrameShow();
                    return true;
                }
                else return false;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void accountFrameShow()
    {
        connected.toolbarBaslikTv.setText("Account");

        AccountFrame accountFrame = new AccountFrame();

        FragmentTransaction frameProcess = getSupportFragmentManager().beginTransaction();

        frameProcess.replace(connected.cercevelerFL.getId(),accountFrame,"Account Frame");

        frameProcess.commit();
    }

    private void favoriteFrameShow()
    {
        connected.toolbarBaslikTv.setText("Favorite");

        FavoriteFrame favoriteFrame = new FavoriteFrame();

        FragmentTransaction frameProcess = getSupportFragmentManager().beginTransaction();

        frameProcess.replace(connected.cercevelerFL.getId(),favoriteFrame,"Favorite Frame");

        frameProcess.commit();
    }

    private void saleFrameShow()
    {
        connected.toolbarBaslikTv.setText("Sale");
    }

    private void messageFrameShow()
    {
        connected.toolbarBaslikTv.setText("Message");

        MessageFrame messageFrame = new MessageFrame();

        FragmentTransaction frameProcess = getSupportFragmentManager().beginTransaction();

        frameProcess.replace(connected.cercevelerFL.getId(),messageFrame,"Message Frame");

        frameProcess.commit();
    }

    private void homeFrameShow()
    {
        connected.toolbarBaslikTv.setText("Home Page");

        HomePageFrame homePageFrame = new HomePageFrame();

        FragmentTransaction frameProcess = getSupportFragmentManager().beginTransaction();

        frameProcess.replace(connected.cercevelerFL.getId(),homePageFrame,"Home Page Frame");

        frameProcess.commit();
    }
}