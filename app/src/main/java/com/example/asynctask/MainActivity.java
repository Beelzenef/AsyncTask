package com.example.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView txtV_Info;
    private Button btn_Sort;
    private Button btn_Cancel;

    private static final int MAX_LENGHT = 2000000;
    private int[] numbers = new int[MAX_LENGHT];

    private int progreso;

    SimpleAsyncTask simpletask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtV_Info = (TextView) findViewById(R.id.txtV_Info);
        btn_Cancel = (Button) findViewById(R.id.btn_Cancel);
        btn_Sort = (Button) findViewById(R.id.btn_Sort);

        generateNumbers();
    }

    private void generateNumbers() {

        Random dado = new Random();

        for (int i = 0; i < MAX_LENGHT; i++) {
            numbers[i] = dado.nextInt();
        }

    }

    public void onClick_Sort(View v) {
        switch (v.getId()) {
            case R.id.btn_SortH:
                // Primera opción: Obteniendo ANR, sin gestión por hilos
                //bubbleSort(numbers);
                //txtV_Info.setText("Operación terminada :)");
                // Segunda opción: Creación de hilo
                //execWithThread();
                // Tercera opción: Operando con AsyncTask
                simpletask = new SimpleAsyncTask();
                simpletask.execute();
                break;
            case R.id.btn_CancelH:
                simpletask.cancel(true);
                break;
        }
    }

    private void execWithThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bubbleSort();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtV_Info.setText("Operación terminada :)");
                    }
                });
            }
        }).start();
    }

    private void bubbleSort() {
        int aux;
        progreso = 0;

        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length - 1; j++) {
                if (numbers[i] > numbers[j]) {
                    aux = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = aux;
                    progreso++;
                }
            }
        }
    }

    private boolean estaCorriendo() {
        return (simpletask != null) && (simpletask.getStatus() == AsyncTask.Status.RUNNING);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Cancelar la simpleTask si está en ejecución, evitando el bugeo de aplicación
        if (estaCorriendo())
            simpletask.cancel(true);
    }

    class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btn_Cancel.setVisibility(View.VISIBLE);
            btn_Sort.setEnabled(false);
            txtV_Info.setText("Iniciando!!");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            btn_Cancel.setVisibility(View.INVISIBLE);
            btn_Sort.setEnabled(true);
            txtV_Info.setText("Operacion cancelada");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btn_Cancel.setVisibility(View.INVISIBLE);
            btn_Sort.setEnabled(true);
            txtV_Info.setText("Operación terminada");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            txtV_Info.setText("En progreso... %");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bubbleSort();
            if (!isCancelled()) {
                publishProgress();
            }
            return null;
        }
    }
}
