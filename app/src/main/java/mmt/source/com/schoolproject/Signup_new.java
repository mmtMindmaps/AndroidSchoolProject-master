package mmt.source.com.schoolproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Signup_new extends AppCompatActivity {
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);

        InitUI();
        InitAction();

    }

    public void InitUI() {
        btn_login = findViewById(R.id.btn_login);
    }

    public void InitAction() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup_new.this, Otp_Activity.class);
                startActivity(i);
            }
        });
    }
}
