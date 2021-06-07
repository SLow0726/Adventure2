package com.slow.adventure.Character;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.view.View;


import com.slow.adventure.ui.ActivityCollector.MyActivity;
import com.slow.adventure.Constant;
import com.slow.adventure.R;
import com.slow.adventure.ui.Role.RoleContentActivity;

import java.util.ArrayList;

public class CharacterSelectActivity extends MyActivity {
    private RecyclerView recycler_view;
    private CharacterSelectAdapter adapter;
    private ArrayList<CharacterItem> mData = new ArrayList();

    ActivityOptionsCompat options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        mData.add(new CharacterItem("Warrior", R.drawable.warrior));
        mData.add(new CharacterItem("Archer", R.drawable.archer));
        mData.add(new CharacterItem("Witch", R.drawable.witch));


        recycler_view = findViewById(R.id.recycle_view_character);

        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CharacterSelectAdapter(mData, new CharacterClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                intentRoleContentActivity(view, position);

            }
        });

        recycler_view.setAdapter(adapter);

    }


    public void intentRoleContentActivity(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(this, RoleContentActivity.class);
        intent.putExtra("position", position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    CharacterSelectActivity.this,
                    Pair.create(view, Constant.TRANSITION_SCENERY_IMAGE_NAME)
            );
            startActivity(intent, options.toBundle());
        }
    }


}