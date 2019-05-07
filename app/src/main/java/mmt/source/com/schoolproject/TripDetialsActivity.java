package mmt.source.com.schoolproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mmt.source.com.schoolproject.Service.GetVehicleDetails;
import mmt.source.com.schoolproject.Service.UserLogin;
import mmt.source.com.schoolproject.fragment.FooterFragment;
import mmt.source.com.schoolproject.model.SvTrack;

public class TripDetialsActivity extends SlidingBarActivity {

    TextView mModuleTitle;
    TextInputEditText driverContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.trip_details_activity, null, false);
        mDrawer.addView(contentView, 0);

        SvTrack svt = SvTrack.getInstance();
        try {
            AsyncTask<String, Void, Integer> result;

            svt.getVehicleDetails().setRouteId("1");
            result = new GetVehicleDetails().execute();
            int code = result.get();
            if (code == 200) {
                EditText routeNum = findViewById(R.id.routeno_edit_text);
                routeNum.setText(svt.getVehicleDetails().getRouteId());

                EditText driverName = findViewById(R.id.drivername_edit_text);
                driverName.setText(svt.getVehicleDetails().getDriverName());

                EditText driverContact = findViewById(R.id.driver_contact_edit_text);
                driverContact.setText(svt.getVehicleDetails().getDriverNum());

                EditText vehNum = findViewById(R.id.vehical_no_edit_text);
                vehNum.setText(svt.getVehicleDetails().getVehicleNum());
            }
        }catch (Exception e) {

        }

        mModuleTitle = findViewById(R.id.product_title);
        mModuleTitle.setText("Trip Details");

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        driverContact = contentView.findViewById(R.id.driver_contact_edit_text);
        driverContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9740047046"));
                startActivity(intent);
            }
        });

    }
}
