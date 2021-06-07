package com.slow.adventure.OkHttp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OkHttpHandler extends AsyncTask<String, Integer, String> {

    // <傳入參數, 處理中更新介面參數, 處理後傳出參數>
    private static final int TIME_OUT = 1000;

    String jsonString1 = "";

    @Override
    protected String doInBackground(String... strings) {
        // 再背景中處理的耗時工作
        try {
            HttpURLConnection conn = null;
            URL url = new URL(strings[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();
            // 讀取資料
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), StandardCharsets.UTF_8));
            jsonString1 = reader.readLine();
            reader.close();

            if (Thread.interrupted()) {
                throw new InterruptedException();

            }
            if (jsonString1.equals("")) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "網路中斷" + e;
        }

        return jsonString1;
    }

    public void onPostExecute(String result) {
        super.onPreExecute();
        // 背景工作處理完"後"需作的事
//            mytest.setText("JSON:\r\n"+ result);
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        // 背景工作處理"中"更新的事

    }


}


