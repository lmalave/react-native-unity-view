package com.reactnative.unity.view;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xzper on 2018-02-07.
 */

public class UnityViewPackage implements ReactPackage {

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
          System.out.println("UUUUUUUU in UnityViewPackage.createViewManagers, reactContext: " + reactContext);
        List<ViewManager> viewManagers = new ArrayList<>();
        viewManagers.add(new UnityViewManager(reactContext));
        return viewManagers;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
           System.out.println("UUUUUUUU in UnityViewPackage.createNativeModules, reactContext: " + reactContext);
       List<NativeModule> modules = new ArrayList<>();
        modules.add(new UnityNativeModule(reactContext));
        return modules;
    }
}
