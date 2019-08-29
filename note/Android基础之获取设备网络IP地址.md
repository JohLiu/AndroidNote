# Android基础之获取设备网络IP地址

标签： Android基础

---

### 1.获取内网IP
```java
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
```

### 2.获取外网IP
```
// manifest.xml
<uses-permission android:name="android.permission.INTERNET" />

// java
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

            // var returnCitySN = {"cip": "123.157.156.122", "cid": "330000", "cname": "浙江省"};
            int start = builder.indexOf("{");
            int end = builder.indexOf("}") + 1;
            String jsonStr = builder.substring(start,end);
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonObject.getString("cip");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "获取地址失败，请重试！！！";
}
```
> 注：在子线程中调用获取外网地址的方法
```java
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        String asd = getOutNetIP(getApplicationContext());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "网络IP是" + asd, Toast.LENGTH_SHORT).show();
            }
        });
    }
});
thread.start();
```