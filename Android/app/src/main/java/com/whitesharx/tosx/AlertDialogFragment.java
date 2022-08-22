package com.whitesharx.tosx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import org.json.JSONException;

@SuppressWarnings("deprecation")
public class AlertDialogFragment extends DialogFragment {
  private static final String DEBUG_TAG = "Tosx";
  private static final String URL_FORMAT = "<a href='%s'>%s</a>";

  private static final String SETTINGS_EXTRA = "SETTINGS_EXTRA";
  private static final String UNITY_OBJECT_EXTRA = "UNITY_OBJECT_EXTRA";

  public static AlertDialogFragment newInstance(SettingsParcelable settings, String unityObject) {
    AlertDialogFragment fragment = new AlertDialogFragment();
    Bundle bundle = fragment.getArguments();

    if (bundle == null) {
      bundle = new Bundle();
    }

    bundle.putParcelable(SETTINGS_EXTRA, settings);
    bundle.putString(UNITY_OBJECT_EXTRA, unityObject);

    fragment.setArguments(bundle);
    fragment.setCancelable(false);

    return fragment;
  }

  public static AlertDialogFragment newInstanceUnsafe(String settingsJson, String unityObject)
      throws JSONException {
    return newInstance(SettingsParcelable.fromJson(settingsJson), unityObject);
  }

  @Override
  public boolean isCancelable() {
    return false;
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    SettingsParcelable settings = getArguments().getParcelable(SETTINGS_EXTRA);
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    String titleText = settings.getTitleText();
    String actionText = settings.getActionText();

    String safeActionText = null != actionText && !actionText.isEmpty()
        ? actionText : getActivity().getString(android.R.string.ok);

    if (null != titleText && !titleText.isEmpty()) {
      builder.setTitle(titleText);
    }

    TextView textView = new TextView(getActivity());
    TypedValue outValue = new TypedValue();

    Resources.Theme theme = getActivity().getTheme();
    DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();

    theme.resolveAttribute(android.R.attr.dialogPreferredPadding, outValue, true);
    int padding = TypedValue.complexToDimensionPixelSize(outValue.data, metrics);

    textView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
    textView.setText(BuildMessage(settings));
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setPadding(padding, padding, padding, padding);

    builder.setView(textView);
    builder.setCancelable(false);

    builder.setPositiveButton(safeActionText, (dialogInterface, i) -> {
      try {
        UnityBridge.CallSafe(new ResultParcelable(ResultParcelable.ACCEPT_RESULT).toJson());
      } catch (JSONException e) {
        e.printStackTrace();
      }
    });

    return builder.create();
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);

    try {
      UnityBridge.CallSafe(new ResultParcelable(ResultParcelable.DISMISS_RESULT).toJson());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private Spanned BuildMessage(SettingsParcelable settings) {
    String messageTextFormat = settings.getMessageTextFormat();

    if (null == messageTextFormat || messageTextFormat.isEmpty()) {
      return new SpannedString("empty message was passed, read the docs...");
    }

    String tosText = settings.getTermsOfServiceText();
    String tosUrl = settings.getTermsOfServiceUrl();

    String ppText = settings.getPrivacyPolicyText();
    String ppUrl = settings.getPrivacyPolicyUrl();

    String tosVar = null != tosText && !tosText.isEmpty()
        ? tosText : SettingsParcelable.TOS_PLACEHOLDER;

    String ppVar = null != ppText && !ppText.isEmpty()
        ? ppText : SettingsParcelable.PP_PLACEHOLDER;

    if (null != tosUrl && !tosUrl.isEmpty()) {
      tosVar = String.format(URL_FORMAT, tosUrl, tosText);
    }

    if (null != ppUrl && !ppUrl.isEmpty()) {
      ppVar = String.format(URL_FORMAT, ppUrl, ppText);
    }

    String messageTemplate = messageTextFormat
        .replace(SettingsParcelable.TOS_PLACEHOLDER, tosVar)
        .replace(SettingsParcelable.PP_PLACEHOLDER, ppVar);

    return Html.fromHtml(messageTemplate);
  }
}
