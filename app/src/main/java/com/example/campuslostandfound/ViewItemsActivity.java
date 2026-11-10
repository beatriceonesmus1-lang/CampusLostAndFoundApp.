package com.example.campuslostandfound;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

public class ViewItemsActivity extends AppCompatActivity {

    TextView tvItems;
    EditText etSearch;
    Spinner spFilterCategory, spFilterStatus;

    String reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        tvItems = findViewById(R.id.tvItems);
        etSearch = findViewById(R.id.etSearch);
        spFilterCategory = findViewById(R.id.spFilterCategory);
        spFilterStatus = findViewById(R.id.spFilterStatus);

        android.content.SharedPreferences preferences =
                getSharedPreferences("LostFoundData", MODE_PRIVATE);

        reports = preferences.getString("reports", "");

        String[] categories = {
                "All Categories", "Electronics",
                "Documents", "Bags", "Clothing"
        };

        String[] statuses = {
                "All", "Lost", "Found"
        };

        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, categories);

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spFilterCategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> statusAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, statuses);

        statusAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spFilterStatus.setAdapter(statusAdapter);

        Runnable filterItems = () -> {

            String search = etSearch.getText().toString()
                    .trim().toLowerCase();

            String category = spFilterCategory.getSelectedItem().toString();
            String status = spFilterStatus.getSelectedItem().toString();

            StringBuilder results = new StringBuilder();

            for (String report : reports.split("\n")) {

                String[] details = report.split(" \\| ", -1);

                if (details.length < 7) {
                    continue;
                }

                boolean matchesSearch =
                        details[2].toLowerCase().contains(search);

                boolean matchesCategory =
                        category.equals("All Categories")
                                || details[1].equals(category);

                boolean matchesStatus =
                        status.equals("All")
                                || details[0].equals(status);

                if (matchesSearch && matchesCategory && matchesStatus) {
                    results.append(report).append("\n");
                }
            }

            if (results.length() == 0) {
                tvItems.setText("No matching items found.");
            } else {
                tvItems.setText(results.toString());
            }
        };

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                filterItems.run();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        AdapterView.OnItemSelectedListener filterListener =
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, android.view.View view,
                            int position, long id) {
                        filterItems.run();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                };

        spFilterCategory.setOnItemSelectedListener(filterListener);
        spFilterStatus.setOnItemSelectedListener(filterListener);

        filterItems.run();
    }
}