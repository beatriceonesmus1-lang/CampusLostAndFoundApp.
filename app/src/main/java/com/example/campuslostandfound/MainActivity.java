

package com.example.campuslostandfound;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLost = findViewById(R.id.btnLost);
        Button btnFound = findViewById(R.id.btnFound);
        Button btnView = findViewById(R.id.btnView);

        btnLost.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportItemActivity.class);
            intent.putExtra("status", "Lost");
            startActivity(intent);
        });

        btnFound.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportItemActivity.class);
            intent.putExtra("status", "Found");
            startActivity(intent);
        });

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewItemsActivity.class);
            startActivity(intent);
        });

    }
}
