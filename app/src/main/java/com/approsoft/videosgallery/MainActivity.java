package com.approsoft.videosgallery;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listVideos;
    ArrayList<String> pathsList;
    ArrayAdapter<String> listAdapter;
    TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listVideos = (ListView) findViewById(R.id.listVideos);
        tvCount = (TextView) findViewById(R.id.tvCount);
        pathsList = getAllMedia();
        listAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, pathsList);
        listVideos.setAdapter(listAdapter);
        tvCount.setText("Videos: "+pathsList.size());
    }

    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = MainActivity.this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do{
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }
}
