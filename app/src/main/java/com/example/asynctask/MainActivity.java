package com.example.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView txtV_Info;

    private static final int MAX_LENGHT = 2000000;
    private int[] numbers = new int[MAX_LENGHT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtV_Info = (TextView) findViewById(R.id.txtV_Info);
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
            case R.id.btn_Sort:
                // Primera opción: Obteniendo ANR, sin gestión por hilos
                //bubbleSort(numbers);
                //txtV_Info.setText("Operación terminada :)");
                // Segunda opción: Creación de hilo
                execWithThread();
                break;
            case R.id.btn_Cancel:
                break;
        }
    }

    private void execWithThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bubbleSort(numbers);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtV_Info.setText("Operación terminada :)");
                    }
                });
            }
        }).start();
    }

    private void bubbleSort(int[] numbers) {
        int aux;
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length - 1; j++) {
                if (numbers[i] > numbers[j]) {
                    aux = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = aux;
                }
            }
        }
    }

    private class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
