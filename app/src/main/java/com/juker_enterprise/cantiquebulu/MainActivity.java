package com.juker_enterprise.cantiquebulu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                selectedFragment).commit();

       bottomNavigationView.setOnItemSelectedListener(navLister);
    }


    private NavigationBarView.OnItemSelectedListener navLister =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    selectedFragment =null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_favoris:
                            selectedFragment = new FavorisFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_apropos:
                            selectedFragment = new InformationFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in,R.anim.slide_out).replace(R.id.fragment_container
                            ,selectedFragment).commit();

                    return true;

                }
            };
    /*
    private NavigationBarView.OnItemReselectedListener navLister =
            new NavigationBarView.OnItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {


                }
            };*/
}