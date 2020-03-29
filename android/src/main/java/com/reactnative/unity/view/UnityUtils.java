package com.reactnative.unity.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import java.util.concurrent.CopyOnWriteArraySet;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by xzper on 2018-03-08.
 */

public class UnityUtils {
    public interface CreateCallback {
        void onReady();
    }

    private static UnityPlayer unityPlayer;
    private static boolean _isUnityReady;
    private static boolean _isUnityPaused;
    private static boolean isUnityLoaded;

    private static final CopyOnWriteArraySet<UnityEventListener> mUnityEventListeners =
            new CopyOnWriteArraySet<>();

    public static UnityPlayer getPlayer() {
        if (!_isUnityReady) {
            return null;
        }
        return unityPlayer;
    }

    public static boolean isUnityReady() {
        return _isUnityReady;
    }

    public static boolean isUnityPaused() {
        return _isUnityPaused;
    }

        public static void loadUnity(Activity mainActivity) {
        System.out.println("UUUUUUUU in UnityUtils.loadUnity, mainActivity: " + mainActivity);
            isUnityLoaded = true;
            Intent intent = new Intent(mainActivity, MainUnityActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            mainActivity.startActivityForResult(intent, 1);
        }

    public static void createPlayer(final Activity activity, final CreateCallback callback) {
        System.out.println("UUUUUUUU in UnityUtils.createPlayer, activity: " + activity);
        if (unityPlayer != null) {
            callback.onReady();
            return;
        }
        System.out.println("UUUUUUUU in UnityUtils.createPlayer, about to call runOnURThread on activity: " + activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
           System.out.println("UUUUUUUU in UnityUtils.createPlayer, in runOnUiThread Runnable.run()");
              System.out.println("UUUUUUUU in UnityUtils.createPlayer, in runOnUiThread Runnable.run(), about to start MainUnityActivity");
              loadUnity(activity);
               System.out.println("UUUUUUUU in UnityUtils.createPlayer, in runOnUiThread Runnable.run(), started  MainUnityActivity ");
           /* activity.getWindow().setFormat(PixelFormat.RGBA_8888);
                int flag = activity.getWindow().getAttributes().flags;
                boolean fullScreen = false;
                if((flag & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                    fullScreen = true;
                }

              System.out.println("UUUUUUUU in UnityUtils.createPlayer, in runOnUiThread Runnable.run(), about to create new UnityPlayer");
               unityPlayer = new UnityPlayer(activity);
              System.out.println("UUUUUUUU in UnityUtils.createPlayer, in runOnUiThread Runnable.run(), created  new UnityPlayer: " + unityPlayer);
*/
                try {
                    // wait a moument. fix unity cannot start when startup.
                    Thread.sleep( 1000 );
                } catch (Exception e) {
                }
/*
                // start unity
                addUnityViewToBackground();
                unityPlayer.windowFocusChanged(true);
                unityPlayer.requestFocus();
                unityPlayer.resume();

                // restore window layout
                if (!fullScreen) {
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                _isUnityReady = true;
                */
                callback.onReady();
            }
        });
    }

    public static void postMessage(String gameObject, String methodName, String message) {
        if (!_isUnityReady) {
            return;
        }
        UnityPlayer.UnitySendMessage(gameObject, methodName, message);
    }

    public static void pause() {
        if (unityPlayer != null) {
            unityPlayer.pause();
            _isUnityPaused = true;
        }
    }

    public static void resume() {
        if (unityPlayer != null) {
            unityPlayer.resume();
            _isUnityPaused = false;
        }
    }

    /**
     * Invoke by unity C#
     */
    public static void onUnityMessage(String message) {
        for (UnityEventListener listener : mUnityEventListeners) {
            try {
                listener.onMessage(message);
            } catch (Exception e) {
            }
        }
    }

    public static void addUnityEventListener(UnityEventListener listener) {
        mUnityEventListeners.add(listener);
    }

    public static void removeUnityEventListener(UnityEventListener listener) {
        mUnityEventListeners.remove(listener);
    }

    public static void addUnityViewToBackground() {
        if (unityPlayer == null) {
            return;
        }
        if (unityPlayer.getParent() != null) {
            ((ViewGroup)unityPlayer.getParent()).removeView(unityPlayer);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            unityPlayer.setZ(-1f);
        }
        final Activity activity = ((Activity)unityPlayer.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(1, 1);
        activity.addContentView(unityPlayer, layoutParams);
    }

    public static void addUnityViewToGroup(ViewGroup group) {
        if (unityPlayer == null) {
            return;
        }
        if (unityPlayer.getParent() != null) {
            ((ViewGroup)unityPlayer.getParent()).removeView(unityPlayer);
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        group.addView(unityPlayer, 0, layoutParams);
        unityPlayer.windowFocusChanged(true);
        unityPlayer.requestFocus();
        unityPlayer.resume();
    }
}
