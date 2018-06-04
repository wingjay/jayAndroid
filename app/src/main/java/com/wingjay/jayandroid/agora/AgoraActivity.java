package com.wingjay.jayandroid.agora;

import android.os.Bundle;
import com.wingjay.jayandroid.BaseActivity;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

/**
 * AgoraActivity
 *
 * @author wingjay
 * @date 2018/05/23
 */
public class AgoraActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建 RtcEngine 对象,并填入 App ID
        RtcEngine rtcEngine = RtcEngine.create(this, "8e8cf2b4d3ff434688155ab6627ef9ed", new RtcEngineEventHandler());

        //设置频道模式为直播
        rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);

        //设置用户角色为主播
        rtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);

        //创建并加入频道
        rtcEngine.joinChannel(null, "wingjayTest", null, myUid)
    }

    class RtcEngineEventHandler extends IRtcEngineEventHandler {

    }
}
