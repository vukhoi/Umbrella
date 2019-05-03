package com.example.umbrella.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbrella.R;
import com.example.umbrella.Util;

import java.util.Random;

import static com.example.umbrella.Util.UNIT;
import static com.example.umbrella.Util.ZIP;
import static com.example.umbrella.Util.verifyZip;



public class UserInputActivity extends AppCompatActivity implements UserInputActivityInterface{
    LinearLayout llZipBtn, llUnitBtn;
    TextView tvZip, tvUnit;
    ZipInputDialog zipInputDialog;
    UnitSelectorInputDialog unitSelectorInputDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        randomizeRequestCode();
        checkInternetPermission();

        llZipBtn = findViewById(R.id.ll_zip_btn);
        llUnitBtn = findViewById(R.id.ll_unit_btn);
        tvZip = findViewById(R.id.tv_zip);
        tvUnit = findViewById(R.id.tv_unit);
        Util.INPUT_UNIT = null;
        Util.INPUT_ZIPCODE = null;

        llZipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipInputDialog = new ZipInputDialog(UserInputActivity.this);
                zipInputDialog.show();
                zipInputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (verifyZip(zipInputDialog.etZip.getText().toString())){
                            Util.INPUT_ZIPCODE = zipInputDialog.etZip.getText().toString();
                            tvZip.setText(Util.INPUT_ZIPCODE);
                            if(Util.INPUT_UNIT != null){
                                startWeatherDetailActivity();
                            }
                        }
                        else{
                            Toast.makeText(UserInputActivity.this, "invalid zip entered, retry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        llUnitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitSelectorInputDialog = new UnitSelectorInputDialog(UserInputActivity.this);
                unitSelectorInputDialog.show();
                unitSelectorInputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String selectedItem = unitSelectorInputDialog.spinnerUnit.getSelectedItem().toString();
                        if (selectedItem != null){
                            Util.INPUT_UNIT = selectedItem;
                            tvUnit.setText(selectedItem);
                            if(Util.INPUT_ZIPCODE != null){
                                startWeatherDetailActivity();
                            }
                        }
                        else{
                            Toast.makeText(UserInputActivity.this, "no unit selected, retry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private void startWeatherDetailActivity() {
        Intent intent = new Intent(this, WeatherDetailActivity.class);
        intent.putExtra(ZIP, Util.INPUT_ZIPCODE);
        if (Util.INPUT_UNIT.equals(getResources().getString(R.string.fahrenheit)))
            intent.putExtra(UNIT, "imperial");
        else if (Util.INPUT_UNIT.equals(getResources().getString(R.string.celsius)))
            intent.putExtra(UNIT,  "metric");
        startActivity(intent);
    }



    public void checkInternetPermission() {
        // check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            Log.d("INTERNET", "requesting permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, Util.INTERNET_PERMISSION_REQUESTCODE);
        }
        Log.d("INTERNET", "permission granted");

    }


    private void randomizeRequestCode() {
        Random random = new Random();
        Util.INTERNET_PERMISSION_REQUESTCODE = random.nextInt(random.nextInt(1000));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("INTERNET", "request granted : " + (PackageManager.PERMISSION_GRANTED == grantResults[0]));
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // restart activity
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
