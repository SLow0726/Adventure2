package com.slow.adventure.ui.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.slow.adventure.Character.CharacterSelectActivity;
import com.slow.adventure.Preferences;
import com.slow.adventure.R;
import com.slow.adventure.ui.Map.MapsActivity;


public class MainActivity extends AppCompatActivity {
    Button btnIntentMap;
    ImageView imageRole;
    String roleID = "";

//    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIntentMap = findViewById(R.id.btnIntentMap);
        imageRole = findViewById(R.id.imageRole);
        btnIntentMap.setOnClickListener(intentMapListener);
        try {
            roleID = new Preferences(this).getValue("Role");
            Log.d("Tag", String.valueOf(roleID));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


//        if (roleID.equals(""))
            intentCharacterSelectActivity();

    }

    public void intentCharacterSelectActivity() {
        Intent intent = new Intent();
        intent.setClass(this, CharacterSelectActivity.class);
        startActivity(intent);
    }

    public void intentMapActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MapsActivity.class);
        startActivity(intent);
    }

    public View.OnClickListener intentMapListener = view -> intentMapActivity();


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TagRe", "onStart");
        try {
            roleID = new Preferences(this).getValue("Role");
            Log.d("Tag", String.valueOf(roleID));

            switch (Integer.parseInt(roleID)) {
                case 0:
                    imageRole.setImageResource(R.drawable.warrior);
                    break;
                case 1:
                    imageRole.setImageResource(R.drawable.archer);
                    break;
                case 2:
                    imageRole.setImageResource(R.drawable.witch);
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}