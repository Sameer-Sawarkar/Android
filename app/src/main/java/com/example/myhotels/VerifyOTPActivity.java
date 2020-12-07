package com.example.myhotels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyOTPActivity extends AppCompatActivity {

    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private String fixedOTP="111111";
    private Button Verify;
    private String inputOtp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        Verify = findViewById(R.id.buttonVerify);

        setupOTPInputs();
        //verifyOTP();
    }

    private void setupOTPInputs(){

        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp +=s.toString();
                    inputCode2.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp +=s.toString();
                    inputCode3.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp +=s.toString();
                    inputCode4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp +=s.toString();
                    inputCode5.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp +=s.toString();
                    inputCode6.requestFocus();
                    //Toast.makeText(VerifyOTPActivity.this, inputOtp, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputOtp +=s.toString();
                    //inputCode6.requestFocus();
                    //Toast.makeText(VerifyOTPActivity.this, inputOtp, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fixedOTP.equals(inputOtp)){
                    Toast.makeText(VerifyOTPActivity.this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show();
                    //Intent intent2 = new Intent(VerifyOTPActivity.this, HotelViewer.class);
                    //startActivity(intent2);
                    inputOtp="";

                }
                else{
                    Toast.makeText(VerifyOTPActivity.this, "Incorrect OTP! Please Try Again.", Toast.LENGTH_SHORT).show();
                    inputOtp ="";
                }

            }
        });
    }

    private void verifyOTP(){

    }
}