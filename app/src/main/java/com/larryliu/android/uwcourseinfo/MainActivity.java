package com.larryliu.android.uwcourseinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String COURSE_CODE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchCourse(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        EditText editText = (EditText) findViewById(R.id.course_code);
        String message = editText.getText().toString();
        intent.putExtra(COURSE_CODE, message);
        startActivity(intent);
    }
}
