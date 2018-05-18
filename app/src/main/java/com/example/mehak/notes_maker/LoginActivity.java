package com.example.mehak.notes_maker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initObj();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboardIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(dashboardIntent);
            }
        });
    }

    private void initObj(){
        LoginButton = (Button)findViewById(R.id.login_btn);
    }
}
