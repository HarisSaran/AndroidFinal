package com.example.advertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InterstitialAddActivity extends AppCompatActivity {

    // We will display all the database records here..
    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[] {DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC};
    final int[] to = new int[] {R.id.id, R.id.title, R.id.desc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        // initialize variables and instance of objects and widgets
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();


        Toolbar myToolbar = findViewById(R.id.fragment_toolbar);
        setSupportActionBar(myToolbar);



        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to,0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // set the onClick for the items in the list (CHECK THIS PART)

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewid) {
                TextView idTextView = view.findViewById(R.id.id);
                TextView titleTextView = view.findViewById(R.id.title);
                TextView descTextView = view.findViewById(R.id.desc);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyMovieActivity.class);
                modify_intent.putExtra("title",title);
                modify_intent.putExtra("desc",desc);
                modify_intent.putExtra("id",id);

                startActivity(modify_intent);
            }
        });


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView idTextView = view.findViewById(R.id.id);
//                TextView titleTextView = view.findViewById(R.id.title);
//                TextView descTextView = view.findViewById(R.id.desc);
//
//                String id = idTextView.getText().toString();
//                String title = titleTextView.getText().toString();
//                String desc = descTextView.getText().toString();
//
//                Intent modify_intent = new Intent(getApplicationContext(), ModifyMovieActivity.class);
//                modify_intent.putExtra("title",title);
//                modify_intent.putExtra("desc",desc);
//                modify_intent.putExtra("id",id);
//
//                startActivity(modify_intent);
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add_record){
            Intent add_mem = new Intent(this,AddMovieActivity.class);
            startActivity(add_mem);
        }

        return super.onOptionsItemSelected(item);
    }
}