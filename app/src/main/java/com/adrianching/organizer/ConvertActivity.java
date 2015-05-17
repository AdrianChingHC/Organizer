package com.adrianching.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.text.TextUtils.substring;



public class ConvertActivity extends ActionBarActivity implements View.OnClickListener {

    Button convertNow, browse;
    TextView listFile;
    String listPath;

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
                if ((listFile.getText().toString()).equals("Select .txt File")) {
                    Toast.makeText(getBaseContext(), "No file is selected.", Toast.LENGTH_LONG).show();
                    break;
                }
                else {

                    //reading text from file
                    try {

                        FileInputStream fileIn = new FileInputStream(listPath);
                        InputStreamReader InputRead = new InputStreamReader(fileIn);
                        BufferedReader bufferedReader = new BufferedReader(InputRead);
                        String line;
                        String dataString = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            String line2 = line.replace("|", " ,");
                            String line3 = line2.replace("$", "\n");
                            dataString = dataString + line3;
                        }



                        try {

                            String dir = Environment.getExternalStorageDirectory().getPath() + "/Organizer";
                            File folder = new File(dir);

                            boolean var = false;
                            if (!folder.exists())
                                var = folder.mkdir();

                            System.out.println("" + var);

                            String fileName = listFile.getText().toString();
                            int pos = fileName.lastIndexOf(".");
                            if (pos > 0) {
                                fileName = fileName.substring(0, pos);
                            }

                            final String filename = folder.toString() + "/" + fileName + ".csv";

                            FileWriter fw = new FileWriter(filename);

                            fw.append(dataString);

                            fw.close();
                            Toast.makeText(getBaseContext(), fileName + ".csv is saved to " + dir, Toast.LENGTH_LONG).show();

                        }
                        catch (IOException ioe)
                        {ioe.printStackTrace();}

                        fileIn.close();



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent listActivity = new Intent(this, ListFileActivity.class);
                    startActivity(listActivity);
                    break;
                }

            case R.id.bBrowse:
                Intent browseFile = new Intent(Intent.ACTION_GET_CONTENT);
                browseFile.setType("text/plain");
                Intent i = Intent.createChooser(browseFile, "File");
                startActivityForResult(i, PICKFILE_RESULT_CODE);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();
                    String FileName = data.getData().getLastPathSegment();
                    if (substring(FileName, FileName.indexOf( "."), FileName.length()).equals(".txt")) {
                        listFile.setText(FileName);
                        listPath = FilePath;
                    }
                    else {
                        Toast.makeText(getBaseContext(), FileName + " is invalid",Toast.LENGTH_LONG).show();
                        FileName = "";
                    }


                }
                break;

        }
    }




}
