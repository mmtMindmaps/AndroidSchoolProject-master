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

import mmt.source.com.schoolproject.fragment.FooterFragment;

public class KidSearchActivity extends SlidingBarActivity {

    TextView mModuleTitle;
    LinearLayout map, video_stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.kid_search_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = findViewById(R.id.product_title);
        mModuleTitle.setText("King Cabs");

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        map = contentView.findViewById(R.id.linear1);
        video_stream = contentView.findViewById(R.id.linear2);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KidSearchActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        video_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KidSearchActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }
}