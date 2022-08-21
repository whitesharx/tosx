package com.whitesharx.tosx;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultParcelable implements Parcelable {
  public static final String ACCEPT_RESULT = "accept";
  public static final String DISMISS_RESULT = "dismiss";
  public static final String VIEW_TOS_RESULT = "view_tos";
  public static final String VIEW_PP_RESULT = "view_pp";

  public static final Creator<ResultParcelable> CREATOR = new Creator<ResultParcelable>() {
    @Override
    public ResultParcelable createFromParcel(Parcel in) {
      return new ResultParcelable(in);
    }

    @Override
    public ResultParcelable[] newArray(int size) {
      return new ResultParcelable[size];
    }
  };

  private final String result;

  public ResultParcelable(String result) {
    this.result = result;
  }

  public String toJson() throws JSONException {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("result", result);

    return jsonObject.toString();
  }

  public String getResult() {
    return result;
  }

  public static ResultParcelable fromJson(String jsonString) throws JSONException {
    JSONObject jsonObject = new JSONObject(jsonString);
    return new ResultParcelable(jsonObject.getString("result"));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeStringArray(new String[]{
        result
    });
  }

  protected ResultParcelable(Parcel in) {
    String[] parcelArray = new String[1];

    in.readStringArray(parcelArray);

    this.result = parcelArray[0];
  }
}
