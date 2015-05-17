package com.adrianching.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
