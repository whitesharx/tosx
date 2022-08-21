package com.whitesharx.tosx;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Debug;
import android.os.PatternMatcher;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.regex.Matcher;

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
    String unityObject = getArguments().getString(UNITY_OBJECT_EXTRA);

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    String titleText = settings.getTitleText();
    String actionText = settings.getActionText();
    String safeActionText = null != actionText && !actionText.isEmpty() ? actionText : "OK";

    if (null != titleText && !titleText.isEmpty()) {
      builder.setTitle(titleText);
    }


    Spanned message = BuildMessage(settings);

    Object[] objSpans = message.getSpans(0, message.length(), Object.class);

    for (Object span : objSpans) {
      Log.d(DEBUG_TAG, "span: " + span);

      if (span instanceof URLSpan) {
        URLSpan urlSpan = (URLSpan) span;
        // urlSpan.

      }

    }

    builder.setMessage(message);
    builder.setPositiveButton(safeActionText, (dialogInterface, i) -> Log.d(DEBUG_TAG, safeActionText));

    // ((TextView)Alert1.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance())

    return builder.create();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);

    Log.d(DEBUG_TAG, "view-created: " + view);



    return view;
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    Log.i(DEBUG_TAG, "Dismiss");
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

    Log.d(DEBUG_TAG, messageTemplate);

    // TODO: Create urlSpans with overridden handler yourselve
    return Html.fromHtml(messageTemplate);
  }
}
