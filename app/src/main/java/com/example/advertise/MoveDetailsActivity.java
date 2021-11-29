package com.example.advertise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MoveDetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_details);

        info = findViewById(R.id.detailed_text);
        image = findViewById(R.id.imageView3);
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            String pass_value = bundle.getString("results2");
            info.setText(pass_value);

            String imageValue = bundle.getString("results");
            info.setText(pass_value);

            Glide.with(this)
                    .load(imageValue)
                    .into(image);
        }
    }
}