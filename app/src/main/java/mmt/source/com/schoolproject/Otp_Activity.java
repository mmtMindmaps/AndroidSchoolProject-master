package mmt.source.com.schoolproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Otp_Activity extends AppCompatActivity {
    Button bSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_);

        InitUI();
        InitAction();
    }

    public void InitUI() {
        bSignup = findViewById(R.id.btn_verify);
    }

    public void InitAction() {
        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Otp_Activity.this,DashBoardActivity.class);
                startActivity(i);
            }
        });
    }
}
