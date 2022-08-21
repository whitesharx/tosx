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

      var settings = new Settings(title, message, tosText, ppText, tosUrl, ppUrl, null);

      var toSx = new Alert(settings);
      await toSx.DisplayAsync();
    }
  }
}
