package com.reactnative.unity.view;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayerActivity;

public abstract class OverrideUnityActivity extends UnityPlayerActivity
{
    public static OverrideUnityActivity instance = null;

    abstract protected void showMainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("UUUUUUUUU in OverrideUnityActivity.onCreate, savedInstanceState: " + savedInstanceState);
        instance = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("UUUUUUUUU in OverrideUnityActivity.onDestroy");
       instance = null;
    }
}
