package com.slow.adventure.ui.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.slow.adventure.ClusterItem.ClusterRenderer;
import com.slow.adventure.ClusterItem.MyParkItem;
import com.slow.adventure.Game.GameActivity;
import com.slow.adventure.OkHttp.APIs;
import com.slow.adventure.OkHttp.OkHttpHandler;
import com.slow.adventure.ParksJSONData.AnalyzeParkDataArrayList;
import com.slow.adventure.ParksJSONData.ParkData;
import com.slow.adventure.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap; //宣告 google map 物件
    float zoom;
    private LocationManager locMgr;
    String bestProv;
    String parkDataResult;
    private ArrayList<ParkData> parkDataArrayList;
    String parkName;
    Double parkLon, parkLat;
    private ClusterManager<MyParkItem> clusterManager;
    private ClusterRenderer clusterRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // 利用 getSupportFragmentManager() 方法取得管理器
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // 以非同步方式取得 GoogleMap 物件
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

//        parkDataArrayList = new AnalyzeParkDataArrayList().getParkDataArrayList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 取得 GoogleMap 物件
        mMap = googleMap;
//        LatLng Taipei101 = new LatLng(25.033611, 121.565000); // 台北 101
        zoom = 17;
//        mMap.addMarker(new MarkerOptions().position(Taipei101).title("台北 101"));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Taipei101, zoom));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);       // 一般地圖
        setUpClusterer();
//        addItems();
        requestPermission();
    }

    // 檢查授權
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {  // Androis 6.0 以上
            // 判斷是否已取得授權
            int hasPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {  // 未取得授權
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        }
        // 如果裝置版本是 Androis 6.0 以下，
        // 或是裝置版本是6.0（包含）以上，使用者已經授權
        setMyLocation(); //  顯示定位圖層
    }

    // 使用者完成授權的選擇以後，會呼叫 onRequestPermissionsResult 方法
    //     第一個參數：請求授權代碼
    //     第二個參數：請求的授權名稱
    //     第三個參數：使用者選擇授權的結果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { //按 允許 鈕
                setMyLocation(); //  顯示定位圖層
            } else {  //按 拒絕 鈕
                Toast.makeText(this, "未取得授權！", Toast.LENGTH_SHORT).show();
                finish();  // 結束應用程式
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //  顯示定位圖層
    private void setMyLocation() throws SecurityException {
        mMap.setMyLocationEnabled(true); // 顯示定位圖層
    }

    @Override
    public void onLocationChanged(Location location) {
        // 取得地圖座標值:緯度,經度
        String x = "緯=" + Double.toString(location.getLatitude());
        String y = "經=" + Double.toString(location.getLongitude());
        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        zoom = 17; //設定放大倍率1(地球)-21(街景)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, zoom));
        Toast.makeText(this, x + "\n" + y, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 取得定位服務
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 取得最佳定位
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);

        // 如果GPS或網路定位開啟，更新位置
        if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER) || locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //  確認 ACCESS_FINE_LOCATION 權限是否授權
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locMgr.requestLocationUpdates(bestProv, 1000, 1, this);
            }
        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  確認 ACCESS_FINE_LOCATION 權限是否授權
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locMgr.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void setUpClusterer() {
        // Position the map.
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(, zoom));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = new ClusterManager<MyParkItem>(this, mMap);
        clusterRenderer = new ClusterRenderer(this, mMap, clusterManager);
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                intentGameActivity();
            }
        });

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }


    private void addItems() {
        OkHttpHandler handler = new OkHttpHandler();
        try {
            parkDataResult = handler.execute(APIs.taipeiParksAPI).get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        parkDataArrayList = new AnalyzeParkDataArrayList(parkDataResult).getParkDataArrayList();
        for (int i = 0; i < parkDataArrayList.size(); i++) {
            parkName = parkDataArrayList.get(i).getParkName();
            parkLat = Double.parseDouble(parkDataArrayList.get(i).getParkLat());
            parkLon = Double.parseDouble(parkDataArrayList.get(i).getParkLon());
            LatLng parkLatLng = new LatLng(parkLat, parkLon);
//            mMap.addMarker(new MarkerOptions().position(parkLatLng).title(parkName));
            MyParkItem parkClusterItem = new MyParkItem(parkLat, parkLon, parkName, parkName);
            clusterManager.addItem(parkClusterItem);

        }
    }


    //    private class RenderClusterInfoWindow extends DefaultClusterRenderer<MyItem> {
//
//        RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
//            super(context, map, clusterManager);
//        }
//
//        @Override
//        protected void onClusterRendered(Cluster<MyItem> cluster, Marker marker) {
//            super.onClusterRendered(cluster, marker);
//        }
//
//        @Override
//        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
//            markerOptions.title(item.getName());
//
//            super.onBeforeClusterItemRendered(item, markerOptions);
//        }
//    }
    public void intentGameActivity() {
        Intent intent = new Intent();
        intent.setClass(this, GameActivity.class);
        startActivity(intent);
    }

}






