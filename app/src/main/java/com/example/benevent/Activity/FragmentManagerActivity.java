package com.example.benevent.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benevent.API.NetworkClient;
import com.example.benevent.R;
import com.example.benevent.ui.fragment.CategoryFragment;
import com.example.benevent.ui.fragment.EventFragment;
import com.example.benevent.ui.fragment.FeedFragment;
import com.example.benevent.ui.fragment.FeedbackFragment;
import com.example.benevent.ui.fragment.ProfilFragment;
import com.example.benevent.ui.fragment.QRcodeFragment;
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

import retrofit2.Retrofit;

public class FragmentManagerActivity extends AppCompatActivity
        implements

        FeedFragment.OnFragmentInteractionListener,

        NavigationView.OnNavigationItemSelectedListener {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Feed");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_feed);
        Fragment fragment = new FeedFragment();
        displaySelectedFragment(fragment);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Attention");
            builder.setMessage("Voulez-vous quitter l'application ?");

            builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            });

            builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SharedPreferences pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        String name = pref.getString("username", "");
        String email = pref.getString("email", "");
        String profilpic = pref.getString("profilpicture", "");

        getMenuInflater().inflate(R.menu.home, menu); // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("TAG", "onResponse: " + profilpic);
        TextView nameV = findViewById(R.id.menu_name);
        TextView emailV = findViewById(R.id.menu_email);
        ImageView pp = findViewById(R.id.menu_pp);
        nameV.setText(name);
        emailV.setText(email);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(profilpic);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            pp.setImageBitmap(bmp);
        } catch (IOException | NetworkOnMainThreadException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //NOTE: creating fragment object
        Fragment fragment = null;

        if (id == R.id.nav_feed) {
            fragment = new FeedFragment();
            setTitle("Feed");
        }

        if (id == R.id.nav_asso) {
            fragment = new CategoryFragment();
            setTitle("Associations");
        }

        if (id == R.id.nav_feedback) {
            fragment = new FeedbackFragment();
            setTitle("Feedback");
        }

        if (id == R.id.nav_event) {
            fragment = new EventFragment();
            setTitle("Event");
        }

        if (id == R.id.nav_qrcode) {
            fragment = new QRcodeFragment();
            setTitle("QR Code");
        }

        if (id == R.id.nav_profil) {
            fragment = new ProfilFragment();
            setTitle("Mon Profil");
        }

        //NOTE: Fragment changing code
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }

        //NOTE:  Closing the drawer after selecting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Ya you can also globalize this variable :P
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LogOut(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Déconnexion");
        builder.setMessage("Voulez-vous vraiment vous déconnecter ?");

        builder.setPositiveButton("Déconnexion", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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
