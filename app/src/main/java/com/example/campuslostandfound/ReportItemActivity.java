package com.example.campuslostandfound;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ReportItemActivity extends AppCompatActivity {

    EditText etItemName, etDescription, etLocation, etDate, etContact;
    Button btnSubmit;
    Spinner spCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_item);

        etItemName = findViewById(R.id.etItemName);
        etDescription = findViewById(R.id.etDescription);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        etContact = findViewById(R.id.etContact);
        btnSubmit = findViewById(R.id.btnSubmit);spCategory = findViewById(R.id.spCategory);

        String[] categories = {
                "Electronics",
                "Documents",
                "Bags",
                "Clothing"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCategory.setAdapter(adapter);



        btnSubmit.setOnClickListener(v -> {

            String name = etItemName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String contact = etContact.getText().toString().trim();

            String category = spCategory.getSelectedItem().toString();


            String status = getIntent().getStringExtra("status");

            if (status == null) {
                status = "Lost";
            }

            if (name.isEmpty() || description.isEmpty()
                    || location.isEmpty() || date.isEmpty()
                    || contact.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            android.content.SharedPreferences preferences =
                    getSharedPreferences("LostFoundData", MODE_PRIVATE);

            String oldReports = preferences.getString("reports", "");

            String newReport = status + " | " + category + " | "
                    + name + " | " + description + " | "
                    + location + " | " + date + " | " + contact;

            String updatedReports = oldReports.isEmpty()
                    ? newReport : oldReports + "\n" + newReport;

            preferences.edit().putString("reports", updatedReports).apply();

            Toast.makeText(this, "Report saved successfully!",
                    Toast.LENGTH_LONG).show();
        });
    }
}



