package com.reactnative.unity.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainUnityActivity extends OverrideUnityActivity {
    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("UUUUUUUUU in MainUnityActivity.onCreate, savedInstanceState: " + savedInstanceState);
        super.onCreate(savedInstanceState);
        UnityUtils.SetPlayer(mUnityPlayer);
        addControlsToUnityFrame();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
         System.out.println("UUUUUUUUU in MainUnityActivity.onNewIntent, intent: " + intent);
       handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
      System.out.println("UUUUUUUUU in MainUnityActivity.handleIntent, intent: " + intent);
        if(intent == null || intent.getExtras() == null) return;

        if(intent.getExtras().containsKey("doQuit"))
            if(mUnityPlayer != null) {
                finish();
            }
    }

    @Override
    protected void showMainActivity() {
      System.out.println("UUUUUUUUU in MainUnityActivity.showMainActivity");
     Activity mainActivity = UnityUtils.getMainActivity();
      System.out.println("UUUUUUUUU in MainUnityActivity.showMainActivity, mainActivity" + mainActivity);
      if (mainActivity != null) {
        Class mainActivityClass = mainActivity.getClass();
        //Class mainActivityClass = com.marathonapp.MainActivity.class;
      System.out.println("UUUUUUUUU in MainUnityActivity.showMainActivity, mainActivityClass" + mainActivityClass);
        Intent intent = new Intent(this, mainActivityClass);
      System.out.println("UUUUUUUUU in MainUnityActivity.showMainActivity, intent" + intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // intent.putExtra("setColor", setToColor);
      System.out.println("UUUUUUUUU in MainUnityActivity.showMainActivity, about to call startActivity on intent: " + intent);
        startActivity(intent);
      System.out.println("UUUUUUUUU in MainUnityActivity.showMainActivity, about to called startActivity: " + intent);
      }
      String closeString = "{\"id\": 1,\"seq\":\"1\",\"name\":\"Close\"}";
      UnityUtils.onUnityMessage(closeString);
    }

    @Override public void onUnityPlayerUnloaded() {
       System.out.println("UUUUUUUUU In MainUnityActivity.onUnityPlayerUnloaded");
       showMainActivity();
    }

    @Override public void onUnityPlayerQuitted() {
      System.out.println("UUUUUUUUU In MainUnityActivity.onUnityPlayerQuitted");
      // showMainActivity();
      // finish();
    }

    public void addControlsToUnityFrame() {
      System.out.println("UUUUUUUUU In MainUnityActivity.addControlsToUnityFrame");
        FrameLayout layout = mUnityPlayer;
        {
            Button myButton = new Button(this);
            myButton.setText("Show Main");
            myButton.setX(10);
            myButton.setY(500);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   showMainActivity();
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Send Msg");
            myButton.setX(320);
            myButton.setY(500);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("Cube", "ChangeColor", "yellow");
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Unload");
            myButton.setX(630);
            myButton.setY(500);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.unload();
                }
            });
            layout.addView(myButton, 300, 200);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Finish");
            myButton.setX(630);
            myButton.setY(800);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            layout.addView(myButton, 300, 200);
        }
    }


}
