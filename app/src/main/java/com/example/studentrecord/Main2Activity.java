package com.example.studentrecord;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain2);

        textView = findViewById(R.id.textView1);

        String value = getIntent().getStringExtra("data");

        textView.setText(value);
    }
}
