package com.adrianching.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button convert, list, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {

        convert = (Button) findViewById(R.id.bConvert);
        list = (Button) findViewById(R.id.bList);
        exit = (Button) findViewById(R.id.bExit);

        convert.setOnClickListener(this);
        list.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.bConvert:
                Intent convertActivity = new Intent(this, ConvertActivity.class);
                startActivity(convertActivity);
                break;

            case R.id.bList:
                String root = Environment.getExternalStorageDirectory().getPath() + "/Organizer";
                File folder = new File(root);
                if(!folder.exists()) {
                    Toast.makeText(getBaseContext(), "No converted files yet.", Toast.LENGTH_LONG).show();
            }   else {
                    Intent listActivity = new Intent(this, ListFileActivity.class);
                    startActivity(listActivity);
            }
                break;

            case R.id.bExit:
                finish();
                break;
        }
    }
}
