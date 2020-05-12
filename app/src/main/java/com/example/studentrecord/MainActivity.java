package com.example.studentrecord;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextId, editTextName, editTextEmail, editTextcc;
    Button buttonAdd, buttonGetData, buttonUpdate, buttonDelete, buttonViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editTextId = findViewById(R.id.editText_id);
        editTextName = findViewById(R.id.editText_name);
        editTextEmail = findViewById(R.id.editText_email);
        editTextcc = findViewById(R.id.editText_CC);

        buttonAdd = findViewById(R.id.button_add);
        buttonGetData = findViewById(R.id.button_view);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonViewAll = findViewById(R.id.button_viewAll);

        AddData();
        ViewData();
        ViewAll();
        updateData();
        deleteData();
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editTextName.getText().toString(),editTextEmail.getText().toString(),editTextcc.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this, "Added Successfully!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ViewData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextId.getText().toString();

                if(id.equals("")){
                    editTextId.setError("Enter id!!");
                    return;
                }

                Cursor cursor = myDb.getData(id);
                String data = null;

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                if(cursor.moveToNext()){
                    data = "ID: "+cursor.getString(0)+"\n"+
                            "NAME: "+cursor.getString(1)+"\n"+
                            "EMAIL: "+cursor.getString(2)+"\n"+
                            "COURSE_COUNT: "+cursor.getString(3)+"\n";
                }
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }

    public void ViewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getAllData();

                StringBuffer sb = new StringBuffer();

                while(cursor.moveToNext()){
                    sb.append("ID :"+cursor.getString(0)+"\n"+
                            "Name :"+cursor.getString(1)+"\n"+
                            "Email :"+cursor.getString(2)+"\n"+
                            "Course Count :"+cursor.getString(3)+"\n");
                }
                showMessage("data", sb.toString());
            }
        });
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = myDb.updateData(editTextId.getText().toString(),
                        editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextcc.getText().toString());

                if(b ==  true){
                    Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "OOPss!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer n = myDb.delete(editTextId.getText().toString());

                if(n>0){
                    Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "OOPPss!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

