package com.example.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import static com.example.asynctask.HiddenActivity.*;

public class HiddenFragment extends Fragment {

    TaskCallback callback;

    private static final int MAX_LENGHT = 2000000;
    private int[] numbers = new int[MAX_LENGHT];

    public static final String TAG = "fragment";

    ProgressBarTask pbt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        generateNumbers();
        pbt = new ProgressBarTask();
        pbt.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (TaskCallback) activity;
    }

    private void generateNumbers() {

        Random dado = new Random();

        for (int i = 0; i < MAX_LENGHT; i++) {
            numbers[i] = dado.nextInt();
        }

    }

    public ProgressBarTask getTarea() {
        return pbt;
    }

    static interface TaskCallback {

        void onPreExecute();
        void onProgressUpdate(int i);
        void onCancelled();
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
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            if (callback != null) {
                callback.onCancelled();
            }
        }

        @Override
        protected Void doInBackground(Void... avoid) {
            if (callback != null && !isCancelled())
            {
                for (int i = 0; i < 100; i++)
                    callback.onProgressUpdate(i);
            }
            return null;
        }
    }
}
