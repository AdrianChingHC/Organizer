package com.adrianching.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ConvertActivity extends ActionBarActivity implements View.OnClickListener {

    Button convertNow, browse;
    TextView listFile;

    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        initialize();
    }

    private void initialize() {

        convertNow = (Button) findViewById(R.id.bConvertNow);
        browse = (Button) findViewById(R.id.bBrowse);
        listFile = (TextView) findViewById(R.id.tvListFile);

        convertNow.setOnClickListener(this);
        browse.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_convert, menu);
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

            case R.id.bConvertNow:

                break;

            case R.id.bBrowse:
                Intent browseFile = new Intent(Intent.ACTION_GET_CONTENT);
                browseFile.setType("file/txt");
                Intent i = Intent.createChooser(browseFile, "File");
                startActivityForResult(i, PICKFILE_RESULT_CODE);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if (resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    listFile.setText(FilePath);
                }
                break;
        }
    }
}
