package com.joh.androidnote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.joh.androidnote.netip.NetIpActivity;
import com.joh.androidnote.richtext.TextViewActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Android基础
 *
 * @author : Joh Liu
 * @date : 2019/8/29 16:58
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * TextView富文本使用
     */
    @OnClick(R.id.btn_text)
    public void richText() {
        startActivity(new Intent(getApplicationContext(), TextViewActivity.class));
    }

    /**
     * 获取当前网络IP
     */
    @OnClick(R.id.btn_net_ip)
    public void netIp() {
        startActivity(new Intent(getApplicationContext(), NetIpActivity.class));
    }
}
