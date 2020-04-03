package com.reactnative.unity.view;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;

/**
 * Created by xzper on 2018-02-07.
 */

public class UnityView extends FrameLayout {

    private UnityPlayer view;

    protected UnityView(Context context) {
        super(context);
        System.out.println("UUUUUUUU in UnityView constructor, context: " + context);
    }

    public void setUnityPlayer(UnityPlayer player) {
        this.view = player;
        System.out.println("UUUUUUUU in UnityView.setUnityPlayer, player: " + player);
        UnityUtils.addUnityViewToGroup(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        System.out.println("UUUUUUUU in UnityView.onWindowFocusChanged, hasWindowFocus: " + hasWindowFocus);
        if (view != null) {
            view.windowFocusChanged(hasWindowFocus);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("UUUUUUUU in UnityView.onConfigurationChanged, hasWindowFocus: " + newConfig);
        if (view != null) {
            view.configurationChanged(newConfig);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        // todo: fix more than one unity view, don't add to background.
        // UnityUtils.addUnityViewToBackground();
        super.onDetachedFromWindow();
        System.out.println("UUUUUUUU in UnityView.onDetachedFromWindow");
    }
}
