package com.example.mentor_youniverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPage extends AppCompatActivity {

    private Button myLogin, myRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        myLogin = (Button) findViewById(R.id.login);
        myRegister = (Button) findViewById(R.id.register);

        myLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginPage.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        myRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginPage.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}