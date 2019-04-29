package com.example.umbrella.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.umbrella.R;

public class ZipInputDialog extends Dialog {
    EditText etZip;
    Button btnSubmit;

    public ZipInputDialog(@NonNull Context context) {
        super(context);
        this.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.zip_input_dialog, null);
        this.setContentView(view);

        etZip = view.findViewById(R.id.et_zip);
        btnSubmit = view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZipInputDialog.this.dismiss();
            }
        });
    }
}
