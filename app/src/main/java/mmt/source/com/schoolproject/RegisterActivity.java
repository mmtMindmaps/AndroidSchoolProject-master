package mmt.source.com.schoolproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.regex.Matcher;

import mmt.source.com.schoolproject.Dao.DaoUtil;
import mmt.source.com.schoolproject.Service.UserRegister;
import mmt.source.com.schoolproject.Util.DbHelper;
import mmt.source.com.schoolproject.model.SvTrack;

public class RegisterActivity extends AppCompatActivity {
    String[] spinnerlist = {"Parent", "Driver"};
    TextView signin;
    MaterialButton regBtn;
    SvTrack svt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.CustomDialogTheme);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, spinnerlist);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.selectspinner);
        materialDesignSpinner.setAdapter(arrayAdapter);

        svt =  SvTrack.getInstance();
        System.out.println("shiva selectes is before "+spinnerlist[0]);
        materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println("shiva selectes is "+spinnerlist[position]);
                svt.getUserDetails().setUsrType(spinnerlist[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        signin=findViewById(R.id.singinId);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, Signup_new.class);
                startActivity(intent);
            }
        });

        regBtn=findViewById(R.id.regBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar bar = findViewById(R.id.processBar);
                bar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EditText usrName = findViewById(R.id.name_edit);
                            if(usrName.getText().toString().length() == 0) {
                                usrName.setError("Please enter name");
                                return;
                            }
                            EditText usrMobile = findViewById(R.id.contactNo_edit_text);
                            if(usrMobile.getText().toString().length() != 10) {
                                usrMobile.setError("Please enter 10 digit mobile number");
                                return;
                            }
                            EditText usrEmail = findViewById(R.id.emailId_edit_text);

                            Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(usrEmail.getText().toString());
                            if(!matcher.matches())
                            {
                                usrEmail.setError("Invalid Email Address");
                                return;
                            }

                            EditText usrAddr = findViewById(R.id.address_edit_text);

                            EditText usrpassword = findViewById(R.id.et_password);
                            if(usrpassword.getText().toString().length() == 0) {
                                usrpassword.setError("Please enter password");
                                return;
                            }

                            svt.getUserDetails().setUsrName(usrName.getText().toString());
                            svt.getUserDetails().setMobNum(usrMobile.getText().toString());
                            svt.getUserDetails().setEmailId(usrEmail.getText().toString());
                            svt.getUserDetails().setUsrAddr(usrAddr.getText().toString());
                            svt.getUserDetails().setPassword(usrpassword.getText().toString());
                            AsyncTask<String, Void, Integer> result;

                            svt.getUserDetails().setAction("Add");
                            result = new UserRegister().execute();
                            int code = result.get();
                            if(code == 200) {

                                DbHelper.createVendorDbHelper(RegisterActivity.this);
                                DaoUtil vendorDetailsDao = new DaoUtil();
                                vendorDetailsDao.addUserDetails();
/*
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.CustomDialogTheme);
                                builder.setTitle("OTP");
                                builder.setMessage("OTP Submitted");

                                String positiveText = getString(android.R.string.ok);
                                builder.setPositiveButton(positiveText,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(RegisterActivity.this, DashBoardActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                String negativeText = "Resend";
                                builder.setNegativeButton(negativeText,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // dismiss dialog, start counter again
                                                dialog.dismiss();
                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();*/
                                Intent intent = new Intent(RegisterActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                bar.setVisibility(View.GONE);
                            }
                        }catch (Exception e) {
                            System.out.println("sada:" +e);
                            Toast.makeText(RegisterActivity.this, "Fatal Registration Failed", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);
                        }

                    }
                },100);


            }
        });

    }
}