package com.example.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class HiddenFragment extends Fragment {

    TaskCallback callback;

    private static final int MAX_LENGHT = 2000000;
    private int[] numbers = new int[MAX_LENGHT];

    public static final String TAG = "fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        generateNumbers();
        ProgressBarTask pbt = new ProgressBarTask();
        pbt.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (TaskCallback) activity;
        }
        catch (ClassCastException e)
        {
            Log.d("ERROR", "Errorcete");
        }
    }

    private void generateNumbers() {

        Random dado = new Random();

        for (int i = 0; i < MAX_LENGHT; i++) {
            numbers[i] = dado.nextInt();
        }

    }

    static interface TaskCallback {

        void onPreExecute();
        void onProgressUpdate(int i);
        void onCancelled(int i);
        void onPostExecute();

    }

    public class ProgressBarTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (callback != null)
            {
                callback.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            if (callback != null)
            {
                callback.onPostExecute();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (callback != null)
            {
                callback.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected Void doInBackground(Void... avoid) {
            return null;
        }
    }
}
