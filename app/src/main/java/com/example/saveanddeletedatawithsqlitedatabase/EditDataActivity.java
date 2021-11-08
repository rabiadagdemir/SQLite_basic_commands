package com.example.saveanddeletedatawithsqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {
    private static final String TAG ="EditDataActivity";
    private Button btnUpdate , btnDelete;
    private EditText editable_item;
    DatabaseHelper mDatabaseHelper;

    //putExtra ile gönderilenleri tutmak için
    private String selectedName;
    private int selectedId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        btnDelete= (Button) findViewById(R.id.btnDelete);
        btnUpdate =(Button) findViewById(R.id.btnUpdate);
        editable_item=(EditText) findViewById(R.id.editable_item);
        mDatabaseHelper= new DatabaseHelper(this);

        Intent receivedIntent= getIntent();
        selectedName=receivedIntent.getStringExtra("name");
        selectedId=receivedIntent.getIntExtra("id",-1);
        //edittexte yazdırıyoruz
        editable_item.setText(selectedName);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item= editable_item.getText().toString();
                if (!item.equals("")){
                    //update method
                    mDatabaseHelper.updateName(item,selectedId,selectedName);
                    editable_item.setText("");
                    toastMessage("data succesfully updated");
                }else{
                    toastMessage("Boş bırakılamaz");
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete method
                mDatabaseHelper.deleteName(selectedId,selectedName);
                editable_item.setText("");
                toastMessage("removed from database");
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
