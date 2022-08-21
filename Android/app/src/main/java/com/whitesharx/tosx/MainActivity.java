package com.whitesharx.tosx;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    String title = "Warning!";
    String message = "By tapping \"OK\" you accept our {_tos_var} and confirm that you've read {_pp_var}.";
    String tosText = "Terms of Service";
    String ppText = "Privacy Policy";
    String tosUrl = "https://say.games/terms-of-use";
    String ppUrl = "https://say.games/privacy-policy";

    SettingsParcelable settings = new SettingsParcelable(title, message, tosText, ppText, tosUrl, ppUrl, null);
    AlertDialogFragment fragment = AlertDialogFragment.newInstance(settings, "FakeObject");

    fragment.show(getFragmentManager(), "tosx-dialog");
  }
}