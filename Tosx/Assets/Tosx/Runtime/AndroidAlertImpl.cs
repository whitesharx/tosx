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

using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using UnityEngine;

namespace Tosx {
  public class AndroidAlertImpl : IAlertImpl {
    private const string BridgeObject = "TosxBridgeObject";

    public event ResultActionReceived ResultActionReceivedEvent;

    private readonly DisplaySettings displaySettings;
    private GameObject bridgeObject;
    private TaskCompletionSource<ResultActionType> resultSource;

    public AndroidAlertImpl(DisplaySettings displaySettings) => this.displaySettings = displaySettings;

    public Task<ResultActionType> DisplayAsync(IEnumerable<ResultActionType> awaitableTypes) {
      resultSource = new TaskCompletionSource<ResultActionType>();

      bridgeObject = new GameObject(BridgeObject);
      var bridgeBehaviour = bridgeObject.AddComponent<BridgeBehaviour>();

      bridgeBehaviour.ResultActionCallback = resultType => {
        ResultActionReceivedEvent?.Invoke(resultType);

        if (awaitableTypes.Any(t => t == resultType)) {
          resultSource.TrySetResult(resultType);
        }
      };

      DisplayAndroidImpl();

      return resultSource.Task;
    }

    public void Dispose() {
      if (null != resultSource) {
        resultSource.TrySetCanceled();
        resultSource = null;
      }

      if (null == bridgeObject) {
        return;
      }

      Object.Destroy(bridgeObject);
      bridgeObject = null;
    }

    private void DisplayAndroidImpl() {
      var jsonArgs = displaySettings.AsJson();

      using var alertClazz = new AndroidJavaClass("com.whitesharx.tosx.AlertDialogFragment");
      using var alertObject = alertClazz.CallStatic<AndroidJavaObject>("newInstanceUnsafe", jsonArgs, BridgeObject);

      using var playerClazz = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
      using var activityObject = playerClazz.GetStatic<AndroidJavaObject>("currentActivity");
      using var fragmentManagerObject = activityObject.Call<AndroidJavaObject>("getFragmentManager");

      alertObject.Call("show", fragmentManagerObject, "tosx-fragment");
    }
  }
}
