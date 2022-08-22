using System;
using UnityEngine;

namespace Tosx {
  public class BridgeBehaviour : MonoBehaviour {
    private void HandleNativeAndroidMessage(string args) {
      Debug.Log($"[Tosx]: Bridge::HandleNativeAndroidMessage: {args}");
      ResultActionCallback?.Invoke(InternalResult.FromJson(args).AsActionType());
    }

    public Action<ResultActionType> ResultActionCallback { get; set; }
  }
}
