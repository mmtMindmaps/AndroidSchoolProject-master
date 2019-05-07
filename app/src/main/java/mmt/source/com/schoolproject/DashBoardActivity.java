package mmt.source.com.schoolproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import mmt.source.com.schoolproject.fragment.FooterFragment;
import mmt.source.com.schoolproject.model.SvTrack;

public class DashBoardActivity extends SlidingBarActivity {

    TextView mModuleTitle;
    LinearLayout myKidDetail, tripDetail, trackMyKid;
    SvTrack svt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dashboard_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = findViewById(R.id.product_title);
        mModuleTitle.setText("King Cabs");

        svt = SvTrack.getInstance();
        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        myKidDetail=contentView.findViewById(R.id.mydetailsid);
        tripDetail=contentView.findViewById(R.id.tripdetailsid);
        trackMyKid=contentView.findViewById(R.id.trackkid);

        myKidDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, MyKidDetailsActivity.class);
                startActivity(intent);
            }
        });

        tripDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                if(svt.getStudentDetails().getRouteId().equals("-1")) {
                    Toast.makeText(DashBoardActivity.this, "Approval is pending", Toast.LENGTH_LONG).show();

                }else */{
                    Intent intent = new Intent(DashBoardActivity.this, TripDetialsActivity.class);
                    startActivity(intent);
                }
            }
        });

        trackMyKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, KidSearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
