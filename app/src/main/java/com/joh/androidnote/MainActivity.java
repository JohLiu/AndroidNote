package com.joh.androidnote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
    public void textView() {
        startActivity(new Intent(getApplicationContext(), TextViewActivity.class));
    }
}
