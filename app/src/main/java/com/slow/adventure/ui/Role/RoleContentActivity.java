package com.slow.adventure.ui.Role;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slow.adventure.Character.CharacterItem;
import com.slow.adventure.Character.CharacterSelectAdapter;
import com.slow.adventure.Preferences;
import com.slow.adventure.ui.ActivityCollector.ActivityCollector;
import com.slow.adventure.ui.ActivityCollector.MyActivity;
import com.slow.adventure.Character.CharacterSelectActivity;
import com.slow.adventure.Constant;
import com.slow.adventure.R;

import java.util.ArrayList;

public class RoleContentActivity extends MyActivity {
    Button btnStart;
    ImageView image, imageViewBack;
    TextView txt;
    int position;
    private RecyclerView recycler_view;
    private RoleContentAdapter adapter;
    private final ArrayList<RoleData> mData = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_content);
        btnStart = findViewById(R.id.btnStart);
        imageViewBack = findViewById(R.id.imageBackToSeclect);
        image = findViewById(R.id.imageRole);
        txt = findViewById(R.id.textRoleName);
        btnStart.setOnClickListener(startListener);
        imageViewBack.setOnClickListener(backListener);


        Intent intent = getIntent();
        position = intent.getIntExtra("position", 555);
//        image.setTransitionName(Constant.TRANSITION_SCENERY_IMAGE_NAME);

        recycler_view = findViewById(R.id.recycle_view_role);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoleContentAdapter(mData, position);
        recycler_view.setAdapter(adapter);

        init();


        mData.add(new RoleData("Warrior", R.drawable.warrior, "        戰士們重裝上陣，與敵人正面交鋒，無視於敵人在他們鎧甲上擦身而過的攻擊。他們運用變化多端的戰術與多款各式武器來保護較脆弱的隊友。怒氣是戰士們強力攻擊的原動力，因此他們必須小心運用怒氣，在戰鬥中發揮最大效益。"));
        mData.add(new RoleData("Archer", R.drawable.archer, "        在黑暗中，永遠會有壹雙眼睛在注視著妳，只要妳值得壹死。這就是弓手的使命。他可以在壹瞬間向敵人射出密集的箭雨，可以利箭破甲、壹箭穿心……最重要的是他可以射得最遠、射得最快，令妳還無法接近他就已經伏屍路上。"));
        mData.add(new RoleData("Witch", R.drawable.witch, "        神秘的魔法師師對圍繞在他們身邊的不同元素感興趣，所以他們掌握火焰、冰寒、雷電三個屬性的元素魔法力量。戰鬥時華麗的魔法攻擊，仿佛一場煙花表演。他們放蕩不羈，生性浪漫又彌漫著藝術氣息，是大陸上最迷人的職業。"));

    }

    public void init() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    public View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            new Preferences(view.getContext()).record("Role", String.valueOf(position));
            finish();
            ActivityCollector.finishOneActivity(CharacterSelectActivity.class.getName());


        }
    };

    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

}