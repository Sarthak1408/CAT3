package com.example.cat3;

import android.content.Intent;
import android.widget.BaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CustomAdapter extends BaseAdapter {
    private Context context;

    LayoutInflater inflater;
    private int[] image;
    private String[] items;
    private int[] cardColors;

    CardView cardView;

    public CustomAdapter(Context context, String[] items, int[] image, int[] cardColors) {
        this.context = context;
        this.items = items;
        this.image = image;
        this.cardColors = cardColors;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null)
        {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item,parent,false);
        }

        ImageView imageView=convertView.findViewById(R.id.grid_item1);
        TextView textView=convertView.findViewById(R.id.t1);
        cardView=convertView.findViewById(R.id.card1);


        imageView.setImageResource(image[position]);
        textView.setText(items[position]);

        int colorIndex = position % cardColors.length;
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, cardColors[colorIndex]));


        /*//set click listener for CardView
        card1=convertView.findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, tips.class);
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }
}
