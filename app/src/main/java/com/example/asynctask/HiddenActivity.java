package com.example.asynctask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HiddenActivity extends AppCompatActivity implements HiddenFragment.TaskCallback {

    private TextView txtV_Info;
    private Button btn_Sort;
    private Button btn_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);

        txtV_Info = (TextView) findViewById(R.id.txtV_Info);
        btn_Cancel = (Button) findViewById(R.id.btn_Cancel);
        btn_Sort = (Button) findViewById(R.id.btn_Sort);
    }

    public void onClick_Sort(View v) {
        switch (v.getId()) {
            case R.id.btn_Sort:
                Fragment fragment = new HiddenFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(fragment, HiddenFragment.TAG);
                fragmentTransaction.commit();

                break;
            case R.id.btn_Cancel:
                break;
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(int i) {

    }

    @Override
    public void onCancelled(int i) {

    }

    @Override
    public void onPostExecute() {

    }
}
