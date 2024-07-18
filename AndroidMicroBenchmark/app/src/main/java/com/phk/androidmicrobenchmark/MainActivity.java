package com.phk.androidmicrobenchmark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRunBenchmark;

        btnRunBenchmark = findViewById(R.id.btnRunBenchmark);

        btnRunBenchmark.setOnClickListener(v -> {
            btnRunBenchmark.setClickable(false);
            runBenchmark();
            btnRunBenchmark.setClickable(true);
        });
    }

    private void runBenchmark() {
        executorService.execute(() -> {
            String[] args = {};
            Benchmark.main(args);
        });

        mainHandler.post(() -> {

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}