package com.slow.adventure.ClusterItem;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class ClusterRenderer extends DefaultClusterRenderer<MyParkItem> implements GoogleMap.OnInfoWindowClickListener {
    ClusterManager<MyParkItem> clusterManager;

    public ClusterRenderer(Context context, GoogleMap map, ClusterManager<MyParkItem> clusterManager) {
        super(context, map, clusterManager);
        this.clusterManager = clusterManager;
    }



    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
    }
}
