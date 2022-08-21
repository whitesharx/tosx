using UnityEngine;

namespace Tosx {
  public class BridgeBehaviour : MonoBehaviour {
    private void HandleNativeAndroidMessage(string args) {
      Debug.Log($"HandleNativeAndroidMessage: {args}");
    }
  }
}
