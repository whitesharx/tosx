// Copyright (c) 2022 Sergey Ivonchik
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
// OR OTHER DEALINGS IN THE SOFTWARE.

using System;
using System.Threading.Tasks;
using UnityEngine;

namespace Tosx {
  public class Alert {
    private readonly Settings settings;
    private GameObject bridgeObject;

    public Alert(Settings settings) => this.settings = settings;

    public void Display(Action onCloseCallback) { }

    public Task DisplayAsync() {
      Debug.Log("[Alert]: DisplayAsync()");

      if (null == bridgeObject) {
        // TODO:
      }

      bridgeObject = new GameObject("TosxObject");
      bridgeObject.AddComponent<BridgeBehaviour>();


      DisplayAndroidImpl();

      return Task.CompletedTask;
    }

    private void DisplayAndroidImpl() {
      var jsonArgs = settings.AsJson();

      Debug.Log($"args: {jsonArgs}");

      using var alertClazz = new AndroidJavaClass("com.whitesharx.tosx.AlertDialogFragment");
      using var alertObject = alertClazz.CallStatic<AndroidJavaObject>("newInstanceUnsafe", jsonArgs, "TosxObject");

      Debug.Log($"obj: {alertObject}");
      LogName(alertObject);

      using var playerClazz = new AndroidJavaClass("com.unity3d.player.UnityPlayer");

      Debug.Log($"playerClazz: {playerClazz}");
      LogName(playerClazz);

      using var activityObject = playerClazz.GetStatic<AndroidJavaObject>("currentActivity");

      Debug.Log($"activityObject: {activityObject}");
      LogName(activityObject);

      using var fragmentManagerObject = activityObject.Call<AndroidJavaObject>("getFragmentManager");

      Debug.Log($"fragmentManagerObject: {fragmentManagerObject}");
      LogName(fragmentManagerObject);

      // fragmentManagerObject.Call("show", alertObject, "tosx-fragment");

      alertObject.Call("show", fragmentManagerObject, "tosx-fragment");
    }

    private void LogName(AndroidJavaObject javaObject) {
      // using var objectClazz = javaObject.Call<AndroidJavaClass>("getClass");
      // var clazzName = objectClazz.Call<string>("getName");

      // Debug.Log($"[Java Class]: {clazzName}");
    }
  }
}
