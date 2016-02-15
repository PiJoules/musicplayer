package com.example.something;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOError;
import java.lang.SecurityException;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.os.Environment;
import android.content.Intent;

public class FilesActivity extends Activity {
    private ListView listView;

    private Button b1;
    
    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.files);

        b1 = (Button)findViewById(R.id.downloadButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent download = new Intent(getApplicationContext(), DownloadActivity.class);
                startActivity(download);
            }
        });
        
        List<File> files = Utils.audioFiles();

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        // Defined Array values to show in ListView
        String[] values = new String[files.size()];
        for (int i = 0; i < files.size(); i++){
            values[i] = files.get(i).toString();
        }

        // Set title for header BEFORE setting adapter
        TextView textView = new TextView(getApplicationContext());
        textView.setText("Files (" + files.size() + ")");
        listView.addHeaderView(textView);

        // Define a new Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.textview, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // // ListView Clicked item index
                // int itemPosition = position;

                // // ListView Clicked item value
                String itemValue = (String)listView.getItemAtPosition(position);

                // // Show Alert 
                // Toast.makeText(getApplicationContext(),
                //     "Position :" + itemPosition + "  ListItem : " + itemValue,
                //     Toast.LENGTH_LONG).show();
                Intent song = new Intent(getApplicationContext(), SongActivity.class);
                song.putExtra("songPath", itemValue);
                startActivity(song);
            }
         }); 
    }

}