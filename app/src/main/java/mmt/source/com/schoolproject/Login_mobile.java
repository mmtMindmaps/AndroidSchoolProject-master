package mmt.source.com.schoolproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import mmt.source.com.schoolproject.Dao.DaoUtil;
import mmt.source.com.schoolproject.Service.GetStuDetails;
import mmt.source.com.schoolproject.Service.UserLogin;
import mmt.source.com.schoolproject.Util.DbHelper;
import mmt.source.com.schoolproject.model.SvTrack;

public class Login_mobile extends AppCompatActivity {
    Button signBtn;
    SvTrack svt;
    TextView sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mobile);

        sign = findViewById(R.id.signup);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_mobile.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        svt = SvTrack.getInstance();
        signBtn=findViewById(R.id.btn_login);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar bar = findViewById(R.id.processBar);
                bar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EditText loginNum = findViewById(R.id.et_phone);
                            if(loginNum.getText().toString().length() == 0) {
                                loginNum.setError("Please enter Mobile Number");
                                return;
                            }
                            EditText loginPwd = findViewById(R.id.et_pass);
                            if(loginPwd.getText().toString().length() == 0) {
                                loginPwd.setError("Please enter password");
                                return;
                            }

                            svt.getUserDetails().setMobNum(loginNum.getText().toString());
                            svt.getUserDetails().setPassword(loginPwd.getText().toString());
                            AsyncTask<String, Void, Integer> result;

                            result = new UserLogin().execute();
                            int code = result.get();
                            if(code == 200) {

                                DbHelper.createVendorDbHelper(Login_mobile.this);
                                DaoUtil vendorDetailsDao = new DaoUtil();
                                vendorDetailsDao.addUserDetails();
                                result = new GetStuDetails().execute();
                                code = result.get();
                                if(code == 200) {

                                    DbHelper.createVendorDbHelper(Login_mobile.this);
                                    vendorDetailsDao.addStuDetails();
                                }
                                Intent intent = new Intent(Login_mobile.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (code == 400) {
                                Toast.makeText(Login_mobile.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                bar.setVisibility(View.GONE);
                            }
                            else {
                                Toast.makeText(Login_mobile.this, "Login Failed", Toast.LENGTH_LONG).show();
                                bar.setVisibility(View.GONE);
                            }
                        }catch (Exception e) {
                            Toast.makeText(Login_mobile.this, "Fatal Login Failed", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);
                        }

                    }
                },100);

            }
        });


    }
}
