package mmt.source.com.schoolproject;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;

import mmt.source.com.schoolproject.Service.StudentRegister;
import mmt.source.com.schoolproject.fragment.FooterFragment;
import mmt.source.com.schoolproject.model.Student;
import mmt.source.com.schoolproject.model.SvTrack;

public class MyKidDetailsActivity extends SlidingBarActivity {

    TextView mModuleTitle;
    MaterialButton submitBtn;
    private int mDay;
    private int mMonth;
    private int mYear;
    private Boolean isDob = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.my_kids_details, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = findViewById(R.id.product_title);
        mModuleTitle.setText("My Kid's Details");

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        EditText stuDob = findViewById(R.id.dob_edit_text);
        stuDob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

                int selectedYear = calendar.get(Calendar.YEAR);
                int selectedMonth = calendar.get(Calendar.MONTH);
                int selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MyKidDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                mDay = selectedDay;
                                mMonth = selectedMonth;
                                mYear = selectedYear;
                                StringBuilder Date = new StringBuilder("");
                                String conver = Integer.toString(selectedYear);
                                Date.append(conver);
                                Date.append("-");
                                selectedMonth++;
                                conver = Integer.toString(selectedMonth);
                                Date.append(conver);
                                Date.append("-");
                                conver = Integer.toString(selectedDay);
                                Date.append(conver);

                                stuDob.setText(Date.toString());
                                isDob = true;
                            }
                        }, mDay, mMonth, mYear);

                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                final Calendar calendar2 = Calendar.getInstance();
                calendar2.set(1970, 1,1);
                datePickerDialog.getDatePicker().setMinDate(calendar2.getTimeInMillis());
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });


        submitBtn=findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SvTrack svt = SvTrack.getInstance();
                EditText stuName = findViewById(R.id.kid_name_edit);
                if(stuName.getText().toString().length() == 0) {
                    stuName.setError("Enter Student Name");
                    return;
                }

                svt.getStudentDetails().setStuName(stuName.getText().toString());
                RadioButton male = findViewById(R.id.male);
                if(male.isChecked())
                    svt.getStudentDetails().setStuGen("Male");
                else
                    svt.getStudentDetails().setStuGen("FeMale");
                EditText stuStd = findViewById(R.id.standard_edit_text);
                if(stuStd.getText().toString().length() == 0) {
                    stuStd.setError("Enter Standard ");
                    return;
                }
                svt.getStudentDetails().setStuStandard(stuStd.getText().toString());


                EditText stuArea = findViewById(R.id.area_edit_text);
                if(stuArea.getText().toString().length() == 0) {
                    stuArea.setError("Enter Area ");
                    return;
                }
                svt.getStudentDetails().setStuArea(stuArea.getText().toString());


                if(stuArea.getText().toString().length() == 0) {
                    stuArea.setError("Enter Area ");
                    return;
                }
                svt.getStudentDetails().setStuDob(stuDob.getText().toString());

                EditText stuPincode = findViewById(R.id.pincode_edit_text);
                if(stuPincode.getText().toString().length() == 0) {
                    stuPincode.setError("Enter Pincode ");
                    return;
                }
                svt.getStudentDetails().setStuPincode(stuPincode.getText().toString());

                EditText stuAddr = findViewById(R.id.kidaddress_edit_text);
                if(stuAddr.getText().toString().length() == 0) {
                    stuAddr.setError("Enter Address ");
                    return;
                }

                svt.getStudentDetails().setStuAddr(stuAddr.getText().toString());

                svt.getStudentDetails().setUsrId(svt.getUserDetails().getUsrId());
                svt.getStudentDetails().setRouteId("-1");
                ProgressBar bar = findViewById(R.id.processBar);
                bar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            AsyncTask<String, Void, Integer> result;
                            SvTrack svt = SvTrack.getInstance();
                            svt.getStudentDetails().setAction("Add");
                            System.out.println("shiva calling student register");
                            result = new StudentRegister().execute();
                            int code = result.get();
                            if(code == 200) {
/*
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyKidDetailsActivity.this, R.style.CustomDialogTheme);
                                builder.setTitle("Thanks for the details");
                                builder.setMessage("Verification is pending");

                                String positiveText = getString(android.R.string.ok);
                                builder.setPositiveButton(positiveText,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(MyKidDetailsActivity.this, DashBoardActivity.class);
                                                startActivity(intent);
                                            }
                                        });

                                String negativeText = "Cancel";
                                builder.setNegativeButton(negativeText,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();
*/
                                Toast.makeText(MyKidDetailsActivity.this, "Approval is pending", Toast.LENGTH_LONG).show();
                                bar.setVisibility(View.GONE);
                                finish();
                            }
                            else {
                                Toast.makeText(MyKidDetailsActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                bar.setVisibility(View.GONE);
                            }
                        }catch (Exception e) {
                            Toast.makeText(MyKidDetailsActivity.this, "Fatal Registration Failed", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);
                        }

                    }
                },100);
            }
        });

    }
}