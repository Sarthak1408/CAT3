package com.example.cat3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class courses extends AppCompatActivity
{
    private GridView gridViewOne;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        gridViewOne = findViewById(R.id.gridViewOne);

        //set up the adapter for the GridView
        String[] items={"Activity_1","Activity_2","Activity_3","Activity_4","Activity_5","Activity_6","Activity_7","Activity_8","Add_Activity"};
        int[] image={R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.add};
        int[] cardColors =
                {
                        R.color.c2,
                        R.color.c2,
                        R.color.c2,
                        R.color.c2,
                        R.color.c2,
                        R.color.c2,
                        R.color.c2,
                        R.color.c2
                };

        CustomAdapter adapter=new CustomAdapter(this,items,image,cardColors);
        gridViewOne.setAdapter(adapter);

        //set item click listener for the GridView
        gridViewOne.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position==0)
                {
                    Intent intent = new Intent(courses.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(courses.this, "Clicked position: " + position, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}