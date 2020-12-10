package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hostel.Util.User;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class signup extends AppCompatActivity {
    EditText edtPhone,editPassword,edtName;
    EditText editEmail; /// Shriyash code ignore this

    Button signUpButton;
    public int phone;
    String AES = "AES";
    String encryptedText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);   /// Shriyash code ignore this                                  // initializing variables
        editPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);  /// Shriyash code ignore this                           //
        signUpButton = (Button) findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");
        try {
            encryptedText = encrypt(editPassword.getText().toString(),edtPhone.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        signUpButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick (View v){


                final ValueEventListener valueEventListener = table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already userphone
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            Toast.makeText(signup.this, "Phone number already exist!!", Toast.LENGTH_SHORT).show();
                        } else {
//                            User user = new User(edtName.getText().toString(), editPassword.getText().toString(), edtPhone.getText().toString());
                            User user = new User(edtName.getText().toString(), encryptedText, edtPhone.getText().toString());

                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(signup.this, "Sign up successful!!", Toast.LENGTH_SHORT).show();
                            Intent loginpage = new Intent( signup.this, LoginAcitvity.class);
                            startActivity(loginpage);
                            finish();



                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });


    }

    //encryption code
    private String encrypt(String Data, String notes_password) throws Exception{
        SecretKey key = generateKey(notes_password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
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