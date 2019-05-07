package mmt.source.com.schoolproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import mmt.source.com.schoolproject.Dao.DaoUtil;
import mmt.source.com.schoolproject.Util.DbHelper;
import mmt.source.com.schoolproject.model.SvTrack;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView logoText;
    private static int splashTimeOut=3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=(ImageView)findViewById(R.id.logo);

        DbHelper.createVendorDbHelper(SplashScreenActivity.this);
        DaoUtil vendorDetailsDao = new DaoUtil();
        vendorDetailsDao.getUserDetails();
        vendorDetailsDao.getStuDetails();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SvTrack svt = SvTrack.getInstance();
                if(svt.getUserDetails().getUsrId().equals("-1")) {
                    Intent i = new Intent(SplashScreenActivity.this, Login_mobile.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashScreenActivity.this, DashBoardActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.move);
        logo.startAnimation(myanim);
    }
}