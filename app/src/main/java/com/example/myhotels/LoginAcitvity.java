package com.example.myhotels;

// LOGIN ACTIVITY JAVA FILE


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myhotels.R;

import com.example.myhotels.Util.CurrentUser;
import com.example.myhotels.Util.Reader;
import com.example.myhotels.Util.User;

public class LoginAcitvity extends AppCompatActivity {
    EditText namer,mypass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitvity);
    }

    //LOGIN METHOD
    public void doLogin(View view){
        User user = null;
        user = Reader.getUserList(getApplicationContext());
        namer = findViewById(R.id.username);
        mypass = findViewById(R.id.mypassword);

        String name = namer.getText().toString();
        String pass = mypass.getText().toString();
        if(user.getUsers().contains(name)  && user.getPasswords().contains(pass) )
        {
            CurrentUser cuser = new CurrentUser(name);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(),"Sorry",Toast.LENGTH_LONG).show();
    }
}
