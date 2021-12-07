package com.example.advertise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;

public class AddMovieActivity extends Activity implements View.OnClickListener {
    // this activity allows the user to add data into the database

    // My Widgets
    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText descEditText;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");
        setContentView(R.layout.activity_add_movie);

        // Instantiate the widgets here
        subjectEditText = findViewById(R.id.subject_edittext);
        descEditText = findViewById(R.id.description_edittext);
        addTodoBtn = findViewById(R.id.add_record);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);

        getIncomingIntent();

    }

//    get intent and put data into field
    private void getIncomingIntent(){
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            String pass_value = bundle.getString("results3");
            subjectEditText.setText(pass_value);

            String desc_EditText = bundle.getString("results2");
            descEditText.setText(desc_EditText);

//            String desc_value = bundle.getString("result2");
//            descEditText.setText(desc_value);

//            String imageValue = bundle.getString("results");
//            info.setText(pass_value);
//
//            Glide.with(this)
//                    .load(imageValue)
//                    .into(image);
        }
    }



    @Override
    public void onClick(View view) {
        // make cases when user clicks record button here
        switch (view.getId()){
            case R.id.add_record:
                final String name = subjectEditText.getText().toString();
                final String desc = descEditText.getText().toString();

                dbManager.insert(name, desc);
                Intent main = new Intent(AddMovieActivity.this, InterstitialAddActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }
}