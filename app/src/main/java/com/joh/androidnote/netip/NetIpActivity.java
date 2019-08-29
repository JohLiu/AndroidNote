package com.joh.androidnote.netip;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.joh.androidnote.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 获取设备网络IP
 *
 * @author : Joh Liu
 * @date : 2019/8/29 17:23
 */
public class NetIpActivity extends AppCompatActivity {

    @BindView(R.id.tv_net_ip_in)
    TextView tvNetIpIn;
    @BindView(R.id.tv_net_ip_out)
    TextView tvNetIpOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_ip);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_net_ip_in)
    public void setTvNetIpIn() {
        String netIpIn = "内网IP：" + getInNetIp(getApplicationContext());
        tvNetIpIn.setText(netIpIn);
    }

    @OnClick(R.id.btn_net_ip_out)
    public void setTvNetIpOut() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String asd = getOutNetIP(getApplicationContext());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvNetIpOut.setText("外网IP：" + asd);
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * 获取内网地址
     */
    public static String getInNetIp(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return intToIp(ipAddress);
        } else {
            return "当前WIFI处于关闭状态，请开启WIFI后再次获取！！！";
        }
    }

    /**
     * 将ip的整数形式转换成ip形式
     */
    private static String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
    }

    /**
     * 获取外网地址
     */
    public static String getOutNetIP(Context context) {
        BufferedReader bufferedReader = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                InputStream inputStream = httpURLConnection.getInputStream();
                //注意编码，会出现乱码
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();
                httpURLConnection.disconnect();

                Log.e("tag", builder.toString());
                // var returnCitySN = {"cip": "123.157.156.122", "cid": "330000", "cname": "浙江省"};
                int start = builder.indexOf("{");
                int end = builder.indexOf("}") + 1;
                String jsonStr = builder.substring(start, end);
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.getString("cip");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "获取地址失败，请重试！！！";
    }
}
