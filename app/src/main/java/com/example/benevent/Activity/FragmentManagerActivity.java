package com.example.benevent.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benevent.R;
import com.example.benevent.ui.fragment.CategoryFragment;
import com.example.benevent.ui.fragment.EventFragment;
import com.example.benevent.ui.fragment.FeedFragment;
import com.example.benevent.ui.fragment.FeedbackFragment;
import com.example.benevent.ui.fragment.ProfilFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

import java.net.URL;

public class FragmentManagerActivity extends AppCompatActivity implements FeedFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        setTitle("Feed");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_feed);
        Fragment feedFragment = new FeedFragment();
        displaySelectedFragment(feedFragment);
    }

    @Override
    public void onBackPressed() {
        int countFragment = getSupportFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (countFragment != 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                alertDialog.setTitle("Attention");
                alertDialog.setMessage("Voulez-vous quitter l'application ?");

                alertDialog.setPositiveButton("OUI", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });

                alertDialog.setNegativeButton("NON", (dialog, which) -> dialog.dismiss());
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        String name = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String profilePicture = sharedPreferences.getString("profilpicture", "");

        getMenuInflater().inflate(R.menu.home, menu);
        TextView nameTV = findViewById(R.id.menu_name);
        TextView emailTV = findViewById(R.id.menu_email);
        ImageView profilePictureIV = findViewById(R.id.menu_pp);
        nameTV.setText(name);
        emailTV.setText(email);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(profilePicture);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            profilePictureIV.setImageBitmap(bitmap);
        } catch (IOException | NetworkOnMainThreadException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_feed :
                fragment = new FeedFragment();
                setTitle("Feed");
                break;
            case R.id.nav_asso :
                fragment = new CategoryFragment();
                setTitle("Associations");
                break;
            case R.id.nav_feedback :
                fragment = new FeedbackFragment();
                setTitle("Feedback");
                break;
            case R.id.nav_event :
                fragment = new EventFragment();
                setTitle("Event");
                break;
            case R.id.nav_profil :
                fragment = new ProfilFragment();
                setTitle("Mon Profil");
                break;
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LogOut(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Déconnexion");
        builder.setMessage("Voulez-vous vraiment vous déconnecter ?");
        builder.setPositiveButton("Déconnexion", (dialog, which) -> {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();

            sharedPrefEditor.putInt("userid", 0);
            sharedPrefEditor.putString("username", "");
            sharedPrefEditor.putString("email", "");
            sharedPrefEditor.putString("profilpicture", "");
            sharedPrefEditor.putString("password", "");

            sharedPrefEditor.apply();
            Intent intent = new Intent(context, SigninActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton("Non", (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
