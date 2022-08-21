package com.whitesharx.tosx;

import android.util.Log;

import java.lang.reflect.Method;

public class UnityBridge {
  private static final String LOG_TAG = UnityBridge.class.getName();

  private static final String DEFAULT_OBJECT = "TosxObject";
  private static final String DEFAULT_METHOD = "HandleNativeAndroidMessage";

  public static void CallSafe(String object, String method, String args) {
    Log.i(LOG_TAG, "CallSafe(" + object + ", " + method + ", " + args + ")");

    try {
      Class<?> clazz = Class.forName("com.unity3d.player.UnityPlayer");
      Method sendMessageMethod = clazz.getMethod("UnitySendMessage", String.class, String.class, String.class);

      sendMessageMethod.invoke(null, object, method, args);

    } catch (Exception e) {
      Log.i(LOG_TAG, "unity bridge, ignoring... " + method);
      Log.e(LOG_TAG, e.getMessage());

      e.printStackTrace();
    }
  }

  public static void CallSafe(String args) {
    CallSafe(DEFAULT_OBJECT, DEFAULT_METHOD, args);
  }
}
