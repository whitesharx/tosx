using JetBrains.Annotations;
using UnityEngine;

namespace Tosx.Sandbox {
  public class SandboxBehaviour : MonoBehaviour {
    [UsedImplicitly]
    private async void Start() {
      Debug.Log("[Sandbox]: Start...");

      var title = "Warning!";
      var message = "By tapping \"OK\" you accept our {_tos_var} and confirm that you've read {_pp_var}.";
      var tosText = "Terms of Service";
      var ppText = "Privacy Policy";
      var tosUrl = "https://say.games/terms-of-use";
      var ppUrl = "https://say.games/privacy-policy";

      var settings = new DisplaySettings(title, message, tosText, ppText, tosUrl, ppUrl, null);
      using var toSx = new TosxAlert(settings);

      toSx.ResultActionReceivedEvent += type => {
        Debug.Log($"[Sandbox]: ResultActionReceivedEvent({type})");
      };

      await toSx.DisplayAsync();

      Debug.Log($"[Sandbox]: Done.");
    }
  }
}
