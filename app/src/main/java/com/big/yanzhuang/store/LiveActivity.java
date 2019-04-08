package com.big.yanzhuang.store;

/**
 * 直播
 * 实时播放网络摄像头画面
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LiveActivity extends AppCompatActivity {
    private TXCloudVideoView mView = null;
//    private Button bt1;
    private TXLivePlayer mPlayer = null;
    private String flvUrl = "http://live.kpcodingoffice.com/live/bonjava.flv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
//        bt1 = findViewById(R.id.bt1);

        Toast.makeText(LiveActivity.this, "未接收到视频信号！", Toast.LENGTH_LONG).show();
//        mView = findViewById(R.id.video_view);
//        mPlayer = new TXLivePlayer(this);
//        mPlayer.setPlayerView(mView);
//        mPlayer.startPlay(flvUrl,TXLivePlayer.PLAY_TYPE_LIVE_FLV);

//        bt1.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        mPlayer.startPlay(flvUrl,TXLivePlayer.PLAY_TYPE_LIVE_FLV);
//    }
}
