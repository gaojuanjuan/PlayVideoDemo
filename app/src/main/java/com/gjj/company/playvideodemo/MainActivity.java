package com.gjj.company.playvideodemo;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, View.OnClickListener {

    private VideoView videoView;
    private String mVideoPath;
    private MediaController mController;
    private RelativeLayout rl;
    private RelativeLayout.LayoutParams layoutParamsFullScreen;
    private boolean isFull;
    private ViewGroup.LayoutParams mLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        rl = ((RelativeLayout) findViewById(R.id.video_play_quan));
//        mVideoPath = "/data/video.mp4";//使用了盒子的本地视频。测试父母类用的，父母乐连不上网所以用了本地视频
        mVideoPath = "http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4";//广电精灵可以用这个在线视频
        File videoFile = new File(mVideoPath);

        mController = new MediaController(this);
        videoView = (VideoView) findViewById(R.id.videoview);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        videoView.setVideoPath(mVideoPath);
        videoView.setMediaController(mController);
        videoView.setOnClickListener(this);
        //全屏

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoView.start();
        videoView.requestFocus();
        mLayoutParams = videoView.getLayoutParams();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("gjj","what = "+what+",extra = "+extra);
        return false;
    }

    //返回键的监听事件
    @Override
    public void onBackPressed() {
        if (isFull){
            isFull = false;
            videoView.setLayoutParams(mLayoutParams);
        }else {
            super.onBackPressed();
        }
    }

    public  int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null)
            videoView.stopPlayback();
        videoView = null;
    }

    @Override
    public void onClick(View v) {
        if (!isFull)
            isFull = true;
        Log.e("gjj","被点击了");
        if (layoutParamsFullScreen == null){
            layoutParamsFullScreen = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParamsFullScreen.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParamsFullScreen.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParamsFullScreen.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParamsFullScreen.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        videoView.setLayoutParams(layoutParamsFullScreen);
    }
}
