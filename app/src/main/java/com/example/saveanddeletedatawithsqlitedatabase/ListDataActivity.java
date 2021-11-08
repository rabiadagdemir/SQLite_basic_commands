package com.example.saveanddeletedatawithsqlitedatabase;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView=(ListView) findViewById(R.id.listView);
        mDatabaseHelper= new DatabaseHelper(this);
        populateListview();

    }
    // get data and append it to a list
    private void populateListview(){
        Log.d(TAG,"populateListView: Displaying data in the Listview");
        //this cursor will return all the rows
        Cursor data= mDatabaseHelper.getData();
        // we created an arrayList to loop through all the rows of the query
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //listede tıklanan satırdaki degeri alsın diye
                String isim = adapterView.getItemAtPosition(position).toString();
                Log.d(TAG, "You clicked on:" + isim);
                Cursor data = mDatabaseHelper.getItemID(isim);// get the id associated with that name
                int itemId = -1;
                while (data.moveToNext()) {
                    itemId = data.getInt(0);
                }
                if (itemId > -1) {
                    Log.d(TAG, "onItemClick: The ID is" + itemId);
                    // ıntent ile diğer sayfaya navigate ediyoruz
                    //diğer sayfada id ve name kullanacağımız için bunları da diğer sayfaya göndermemiz gerekiyo
                    Intent editScreenIntent= new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemId);
                    editScreenIntent.putExtra("name",isim);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No Id asssociated with that name");
                }
            }
        });


    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
