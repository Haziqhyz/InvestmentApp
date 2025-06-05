package com.example.investmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText investInput, rateInput, monthsInput;
    TextView monthlyResult, totalResult;
    Button calculateBtn, aboutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        investInput = findViewById(R.id.investAmountInput);
        rateInput = findViewById(R.id.dividendRateInput);
        monthsInput = findViewById(R.id.monthsInput);
        monthlyResult = findViewById(R.id.monthlyResultText);
        totalResult = findViewById(R.id.totalResultText);
        calculateBtn = findViewById(R.id.calculateButton);
        aboutBtn = findViewById(R.id.menu_about);
        setSupportActionBar(findViewById(R.id.toolbar));

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDividend();
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void calculateDividend() {
        try {
            double investedAmount = Double.parseDouble(investInput.getText().toString());
            double annualRate = Double.parseDouble(rateInput.getText().toString());
            int months = Integer.parseInt(monthsInput.getText().toString());

            if (months < 1 || months > 12) {
                Toast.makeText(this, "Months must be between 1 and 12", Toast.LENGTH_SHORT).show();
                return;
            }

            double monthlyDividend = (annualRate / 100.0 / 12.0) * investedAmount;
            double totalDividend = monthlyDividend * months;

            DecimalFormat df = new DecimalFormat("0.00");
            monthlyResult.setText("Monthly Dividend: RM " + df.format(monthlyDividend));
            totalResult.setText("Total Dividend: RM " + df.format(totalDividend));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Inside public class AboutActivity extends AppCompatActivity {
// ...

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_home) {

            return true;
        } else if (itemId == R.id.menu_about) {
            startActivity (new Intent(this, AboutActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }




// ...
// }

}
