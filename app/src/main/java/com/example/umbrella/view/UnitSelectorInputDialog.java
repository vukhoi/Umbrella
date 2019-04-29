package com.example.umbrella.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.umbrella.R;

public class UnitSelectorInputDialog extends Dialog {
    Spinner spinnerUnit;
    Button btnSubmit;

    UnitSelectorInputDialog(@NonNull Context context) {
        super(context);
        this.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.unit_selector_dialog, null);
        this.setContentView(view);

        spinnerUnit = view.findViewById(R.id.spinner_unit);
        btnSubmit = view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnitSelectorInputDialog.this.dismiss();
            }
        });
    }

}
