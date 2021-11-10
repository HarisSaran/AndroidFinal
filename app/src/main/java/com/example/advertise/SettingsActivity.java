package com.example.advertise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {
    private View parentView;
    private TextView themeTV, titleTV;
    private SwitchMaterial clrTextSwitch, sizeTextSwitch, darkModeSwitch, themeSwitch; //, changeImagesSwitch;
    SharedPreferences sharedPreferences;

    private UserSettings settings;
    String text_color = "";
    String text_size = "";
    String dark_mode = "";
    String change_images = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        settings = (UserSettings) getApplication();
        initWidgets();
        loadSharedPreferences();
        initSwitchListener();

        colorSwitchListener();
        sizeSwitchListener();
        darkModeSwitchListener();
//        changeImagesSwitchListener();

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(OptionsActivity.this, "GOBACK", Toast.LENGTH_SHORT).show();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.text_size),text_size);
                editor.putString(getString(R.string.font_color),text_color);
                editor.putString(getString(R.string.dark_mode),dark_mode);
                editor.putString(getString(R.string.change_images),change_images);


                editor.apply();

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Up Button (Back)
//        getSupportActionBar().setTitle("ListActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        return super.onCreateOptionsMenu(menu);
    }

    private void initWidgets(){
        themeTV = findViewById(R.id.themeTV);
        titleTV = findViewById(R.id.titleTV);
        themeSwitch = findViewById(R.id.themeSwitch);
        parentView = findViewById(R.id.parentView);
        clrTextSwitch = findViewById(R.id.switch_txcolor);
        sizeTextSwitch = findViewById(R.id.switch_txsize);
        darkModeSwitch = findViewById(R.id.switch_dark_mode);
//        changeImagesSwitch = findViewById(R.id.switch_change_images);
    }

    private void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME, UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
    }

    private void updateView()
    {
        final int black = ContextCompat.getColor(this, R.color.cardview_dark_background);
        final int white = ContextCompat.getColor(this, R.color.white);

        if(settings.getCustomTheme().equals(UserSettings.DARK_THEME))
        {
            titleTV.setTextColor(white);
            themeTV.setTextColor(white);
            themeTV.setText("Dark");
            parentView.setBackgroundColor(black);
            themeSwitch.setChecked(true);
//            clrTextSwitch.setChecked(true);
//            sizeTextSwitch.setChecked(true);
        }
        else
        {
            titleTV.setTextColor(black);
            themeTV.setTextColor(black);
            themeTV.setText("Light");
            parentView.setBackgroundColor(white);
            themeSwitch.setChecked(false);
//            clrTextSwitch.setChecked(false);
//            sizeTextSwitch.setChecked(false);
        }

    }

    private void initSwitchListener(){
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    settings.setCustomTheme(UserSettings.DARK_THEME);
                else
                    settings.setCustomTheme(UserSettings.LIGHT_THEME);

                SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(UserSettings.CUSTOM_THEME, settings.getCustomTheme());
                editor.apply();
                updateView();
            }
        });
    }

    public void colorSwitchListener(){
        clrTextSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    text_color = "yellow";
                else
                    text_color = "pink";
            }
        });
    }

    public void sizeSwitchListener(){
        sizeTextSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
//                    settings.setCustomTheme(UserSettings.DARK_THEME);
                    text_size = "big";
                else
                    text_size = "small";
            }
        });
    }

    public void darkModeSwitchListener(){
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    dark_mode = "dark";
            }
        });
    }

//    public void changeImagesSwitchListener(){
//        changeImagesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                if(checked)
//                    change_images = "change";
//            }
//        });
//    }

}