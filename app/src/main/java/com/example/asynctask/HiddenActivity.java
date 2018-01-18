package com.example.asynctask;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HiddenActivity extends AppCompatActivity implements HiddenFragment.TaskCallback {

    private TextView txtV_Info;
    private Button btn_Sort;
    private Button btn_Cancel;
    private ProgressBar progressBar;

    HiddenFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);

        txtV_Info = (TextView) findViewById(R.id.txtV_InfoH);
        btn_Cancel = (Button) findViewById(R.id.btn_CancelH);
        btn_Sort = (Button) findViewById(R.id.btn_SortH);
        progressBar = (ProgressBar) findViewById(R.id.progB_ProgresoH);
        progressBar.setMax(100);

        fragment = new HiddenFragment();
    }

    public void onClick_SortH(View v) {
        switch (v.getId()) {
            case R.id.btn_SortH:

                if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(fragment, HiddenFragment.TAG);
                        fragmentTransaction.commit();
                }
                break;
            case R.id.btn_CancelH:
                cancelarTarea();
                break;
        }
    }

    private void cancelarTarea() {
        if (fragment != null) {
            if (fragment.getTarea().getStatus() == AsyncTask.Status.RUNNING) {
                fragment.getTarea().cancel(true);
            }
        }
    }

    @Override
    public void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        btn_Cancel.setVisibility(View.VISIBLE);
        btn_Cancel.setEnabled(true);
        btn_Sort.setEnabled(false);
        txtV_Info.setText("Iniciando...");
    }

    @Override
    public void onProgressUpdate(int i) {
        progressBar.setProgress(i);

    }

    @Override
    public void onCancelled() {
        txtV_Info.setText("Proceso cancelado");
        btn_Sort.setEnabled(true);
        btn_Cancel.setEnabled(false);
        btn_Cancel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPostExecute() {
        txtV_Info.setText("Operación finalizada");
        progressBar.setVisibility(View.INVISIBLE);
        btn_Sort.setEnabled(true);
        btn_Cancel.setEnabled(false);
        btn_Cancel.setVisibility(View.INVISIBLE);
    }
}
