package com.example.hostel;

// LOGIN ACTIVITY JAVA FILE


import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;

import com.example.hostel.Util.CurrentUser;
import com.example.hostel.Util.User;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class LoginAcitvity extends AppCompatActivity {
    private long backPressTime;
    String AES = "AES",fdecryPass;
    String decryptedPass;
    EditText edtPhone,edtPassword;
    Button login,gotosignup;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitvity);


        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        login = (Button)findViewById(R.id.button);

        gotosignup = (Button)findViewById(R.id.button2);
        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        try {
             decryptedPass = decrypt(edtPassword.getText().toString(), edtPhone.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosignuppage = new Intent(LoginAcitvity.this, signup.class);
                startActivity(gotosignuppage);
                finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table_user.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user not exist in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            // get user information
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            try {
                                fdecryPass = decrypt(user.getPassword(),edtPhone.getText().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (fdecryPass == decryptedPass) {

                                CurrentUser cuser = new CurrentUser(edtPhone.getText().toString());
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                //Intent MainPage = new Intent( LoginAcitvity.this, MainActivity.class);
                                //common.currentUser = user;
                                //startActivity(MainPage);
                                Toast.makeText(getApplicationContext(), "Sign in Successfullly !", Toast.LENGTH_SHORT).show();
                                finish();
                                //Toast.makeText(getApplicationContext(), "Sign in Successfullly !", Toast.LENGTH_SHORT).show();
                                user.setPhoneNumber(edtPhone.getText().toString());


                            } else {
                                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            Toast.makeText(LoginAcitvity.this, "user not exist please sign up first", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (backPressTime+2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else{
            Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT).show();
        }

        backPressTime = System.currentTimeMillis();
    }

    //decryption code
    private String decrypt(String outputstring, String Password) throws Exception {
        SecretKey key = generateKey(Password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeValue = Base64.decode(outputstring, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodeValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    // common code for encryption and decryption key generation
    private SecretKey generateKey(String notes_password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = notes_password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKey secretKeySpec =  new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}