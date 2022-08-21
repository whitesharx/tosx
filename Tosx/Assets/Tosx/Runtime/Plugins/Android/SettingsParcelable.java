package com.whitesharx.tosx;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsParcelable implements Parcelable {
  public static final String TOS_PLACEHOLDER = "{_tos_var}";
  public static final String PP_PLACEHOLDER = "{_pp_var}";

  public static final Creator<SettingsParcelable> CREATOR = new Creator<SettingsParcelable>() {
    @Override
    public SettingsParcelable createFromParcel(Parcel in) {
      return new SettingsParcelable(in);
    }

    @Override
    public SettingsParcelable[] newArray(int size) {
      return new SettingsParcelable[size];
    }
  };

  private final String titleText;
  private final String messageTextFormat;
  private final String termsOfServiceText;
  private final String privacyPolicyText;
  private final String termsOfServiceUrl;
  private final String privacyPolicyUrl;
  private final String actionText;

  public SettingsParcelable(String titleText, String messageTextFormat, String termsOfServiceText,
                            String privacyPolicyText, String termsOfServiceUrl, String privacyPolicyUrl,
                            String actionText) {
    this.titleText = titleText;
    this.messageTextFormat = messageTextFormat;
    this.termsOfServiceText = termsOfServiceText;
    this.privacyPolicyText = privacyPolicyText;
    this.termsOfServiceUrl = termsOfServiceUrl;
    this.privacyPolicyUrl = privacyPolicyUrl;
    this.actionText = actionText;
  }

  public String getTitleText() {
    return titleText;
  }

  public String getMessageTextFormat() {
    return messageTextFormat;
  }

  public String getTermsOfServiceText() {
    return termsOfServiceText;
  }

  public String getPrivacyPolicyText() {
    return privacyPolicyText;
  }

  public String getTermsOfServiceUrl() {
    return termsOfServiceUrl;
  }

  public String getPrivacyPolicyUrl() {
    return privacyPolicyUrl;
  }

  public String getActionText() {
    return actionText;
  }

  public static SettingsParcelable fromJson(String jsonString) throws JSONException {
    JSONObject jsonObject = new JSONObject(jsonString);

    String titleText = jsonObject.getString("titleText");
    String messageTextFormat = jsonObject.getString("messageTextFormat");
    String termsOfServiceText = jsonObject.getString("termsOfServiceText");
    String privacyPolicyText = jsonObject.getString("privacyPolicyText");
    String termsOfServiceUrl = jsonObject.getString("termsOfServiceUrl");
    String privacyPolicyUrl = jsonObject.getString("privacyPolicyUrl");
    String actionText = jsonObject.getString("actionText");

    return new SettingsParcelable(titleText, messageTextFormat, termsOfServiceText,
        privacyPolicyText, termsOfServiceUrl, privacyPolicyUrl, actionText);
  }

  public String toJson() throws JSONException {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("titleText", titleText);
    jsonObject.put("messageTextFormat", messageTextFormat);
    jsonObject.put("termsOfServiceText", termsOfServiceText);
    jsonObject.put("privacyPolicyText", privacyPolicyText);
    jsonObject.put("termsOfServiceUrl", termsOfServiceUrl);
    jsonObject.put("privacyPolicyUrl", privacyPolicyUrl);
    jsonObject.put("actionText", actionText);

    return jsonObject.toString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeStringArray(new String[]{
        titleText,
        messageTextFormat,
        termsOfServiceText,
        privacyPolicyText,
        termsOfServiceUrl,
        privacyPolicyUrl,
        actionText
    });
  }

  protected SettingsParcelable(Parcel in) {
    String[] parcelArray = new String[7];

    in.readStringArray(parcelArray);

    this.titleText = parcelArray[0];
    this.messageTextFormat = parcelArray[1];
    this.termsOfServiceText = parcelArray[2];
    this.privacyPolicyText = parcelArray[3];
    this.termsOfServiceUrl = parcelArray[4];
    this.privacyPolicyUrl = parcelArray[5];
    this.actionText = parcelArray[6];
  }
}
