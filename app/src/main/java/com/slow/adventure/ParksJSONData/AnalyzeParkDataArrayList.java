package com.slow.adventure.ParksJSONData;

import android.util.Log;

import com.slow.adventure.OkHttp.APIs;
import com.slow.adventure.OkHttp.OkHttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnalyzeParkDataArrayList {

    ArrayList<ParkData> arrayList = new ArrayList();
    String result;

    public AnalyzeParkDataArrayList(String result) {
        this.result = result;

    }


    public ArrayList<ParkData> getParkDataArrayList() {
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String parkName = jsonObject.getString("pm_name");
                String parkLat = jsonObject.getString("pm_lat");
                String parkLon = jsonObject.getString("pm_lon");
                if (parkName.equals("花卉試驗中心")){
                    parkLat = "25.13501543281401";
                }
                else if (parkName.equals("北投社三層崎公園")){
                    parkLat = "25.1459066";
                    parkLon = "121.4917504";
                }
                arrayList.add(new ParkData(parkName, parkLon, parkLat));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
