package mmt.source.com.schoolproject;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog mDialog;
    VideoView videoView;
    ImageButton imageButton;

    String videoUrl = "http://62.176.195.157/mjpg/video.mjpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        videoView = (VideoView) findViewById(R.id.videoView);
        imageButton = (ImageButton) findViewById(R.id.btn_play);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please Wait");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        try {
            if( !videoView.isPlaying()) {
                Uri uri = Uri.parse(videoUrl);
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        imageButton.setImageResource(R.drawable.ic_play);
                    }
                });
            }
            else {
                videoView.pause();
                imageButton.setImageResource(R.drawable.ic_play);
            }
        }
        catch (Exception e) {

        }
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mDialog.dismiss();
                mp.setLooping(true);
                videoView.start();
                imageButton.setImageResource(R.drawable.ic_pause);

            }
        });
    }
}
