package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hostel.Util.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    EditText edtPhone,editPassword,edtName, edtEmail;
    FirebaseAuth fAuth;
    Button signUpButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        editPassword = (EditText) findViewById(R.id.edtPassword);
        signUpButton = (Button) findViewById(R.id.btnSignUp);
        loginButton=(Button) findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();

        /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");
        try {
            encryptedText = encrypt(editPassword.getText().toString(),edtPhone.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String password = editPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    edtEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    editPassword.setError("Phone is required");
                    return;
                }
                if(password.length()<5) {
                    editPassword.setError("Password must be >5 characters");
                    return;
                }

                //register the user into firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(signup.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(signup.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginAcitvity.class));
            }
        });
    }



    /*//encryption code
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
    }*/
}