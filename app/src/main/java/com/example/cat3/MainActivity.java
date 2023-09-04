package com.example.cat3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    ImageView navigationImage;
    DrawerLayout drawerlayout;
    NavigationView navigationview;

    BottomNavigationView bottomnav;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        navigationImage=findViewById(R.id.navigationImage);
        drawerlayout=findViewById(R.id.drawerlayout);
        navigationview=findViewById(R.id.navigationview);
        bottomnav=findViewById(R.id.bottomnav);

        //for appbar with option menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        navigationImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();

                if(id==R.id.profile)
                {
                    Intent intent=new Intent(MainActivity.this, profile.class);
                    startActivity(intent);
                }
                else if (id==R.id.logout)
                {
                    Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                return true;
            }
        });

        //for home fragment
        // Check if the fragmentContainer is null (for smaller screens)
        if (findViewById(R.id.fragmentContainer) != null)
        {
            if (savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new home())
                        .commit();
            }
        }

        //for bottomnavigation
        bottomnav.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.courses)
            {
                Intent intent = new Intent(MainActivity.this, courses.class);
                startActivity(intent);
            }
            else if(item.getItemId() == R.id.attendance)
            {

            }
            else if(item.getItemId() == R.id.feedback)
            {

            }
            else if (item.getItemId() == R.id.setting)
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.fragmentContainer, new setting())
                        .addToBackStack(null)
                        .commit();
            }
            return true;
        });




    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        //inflate the menu ; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menufile,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        if(id==R.id.home)
        {
            Intent intent=new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.feedback)
        {
            Intent intent=new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

}