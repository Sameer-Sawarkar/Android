package com.example.myhotels;

// LOGIN ACTIVITY JAVA FILE


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myhotels.common.common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myhotels.R;

import com.example.myhotels.Util.CurrentUser;
import com.example.myhotels.Util.Reader;
import com.example.myhotels.Util.User;

public class LoginAcitvity extends AppCompatActivity {

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
                            if (user.getPassword().equals(edtPassword.getText().toString())) {

                                Intent MainPage = new Intent( LoginAcitvity.this, MainActivity.class);
                                common.currentUser = user;
                                startActivity(MainPage);
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
}