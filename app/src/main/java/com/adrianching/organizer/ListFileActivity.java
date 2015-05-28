package com.adrianching.organizer;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ListFileActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MyFragment fragment = new MyFragment();
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
        setContentView(R.layout.activity_list);
    }

    public static class MyFragment extends ListFragment {

        private List<String> item = null;
        private List<String> path = null;
        private String root;

        private void initialize() {

            root = Environment.getExternalStorageDirectory().getPath() + "/Organizer";
            getDir(root);

        }

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initialize();
        }

        private void getDir(String dirPath) {
            item = new ArrayList<String>();
            path = new ArrayList<String>();
            File f = new File(dirPath);
            File[] files = f.listFiles();

	        if(!dirPath.equals(root)){
	            item.add(root);
	            path.add(root);
	        }

            for (int i=0; i < files.length; i++){
                File file = files[i];

                if (!file.isHidden() && file.canRead()){
                    path.add(file.getPath());
                    if (!file.isDirectory()){
                        item.add(file.getName());
                    }
                }
            }
          ArrayAdapter<String> fileList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, item);
          setListAdapter(fileList);
        }

        public void onListItemClick(ListView l, View v, int position, long id){
            File file = new File(path.get(position));

            if (file.isDirectory()){
                if (file.canRead()){
                    getDir(path.get(position));
                }
                else {
                    new AlertDialog.Builder(getActivity()).setTitle("[" + file.getName() + "] folder can't be read!").setPositiveButton("OK", null).show();
                }
            }else {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File files = new File(path.get(position));
                intent.setDataAndType(Uri.fromFile(files), "text/csv");
                startActivity(intent);
            }
        }
    }
}
