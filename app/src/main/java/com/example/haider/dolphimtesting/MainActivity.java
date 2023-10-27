package com.example.haider.dolphimtesting;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.haider.dolphimtesting.CaptureClasses.Capture;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
    Button btnScan;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan=findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaneCode();
            }
        });

    }

    private void scaneCode() {
        ScanOptions options=new ScanOptions();
        options.setPrompt("Volume Up to Turn On Flash Light");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(Capture.class);
        barLauncher.launch(options);//This will call the scan class
    }

    ActivityResultLauncher<ScanOptions> barLauncher=registerForActivityResult(new ScanContract(),result -> {
        if(result.getContents()!=null){
            Intent intent=new Intent(MainActivity.this,API_Response_Activity.class);
            intent.putExtra("QR",result.getContents());
            startActivity(intent);
//            finish();
        }
    });

}