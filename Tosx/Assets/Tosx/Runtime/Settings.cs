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

// ReSharper disable ConvertToAutoPropertyWithPrivateSetter

using System;
using UnityEngine;

namespace Tosx {
  [Serializable]
  public partial class Settings {
    [SerializeField]
    private string titleText;

    [SerializeField]
    private string messageTextFormat;

    [SerializeField]
    private string termsOfServiceText;

    [SerializeField]
    private string privacyPolicyText;

    [SerializeField]
    private string termsOfServiceUrl;

    [SerializeField]
    private string privacyPolicyUrl;

    [SerializeField]
    private string actionText;

    public Settings(string titleText, string messageTextFormat,
      string termsOfServiceText, string privacyPolicyText, string termsOfServiceUrl,
      string privacyPolicyUrl, string actionText) {
      this.titleText = titleText;
      this.messageTextFormat = messageTextFormat;
      this.termsOfServiceText = termsOfServiceText;
      this.privacyPolicyText = privacyPolicyText;
      this.termsOfServiceUrl = termsOfServiceUrl;
      this.privacyPolicyUrl = privacyPolicyUrl;
      this.actionText = actionText;
    }

    public string TitleText => titleText;
    public string MessageTextFormat => messageTextFormat;
    public string TermsOfServiceText => termsOfServiceText;
    public string PrivacyPolicyText => privacyPolicyText;
    public string TermsOfServiceUrl => termsOfServiceUrl;
    public string PrivacyPolicyUrl => privacyPolicyUrl;
    public string ActionText => actionText;
  }

  public partial class Settings {
    public Settings FromJson(string jsonString) => JsonUtility.FromJson<Settings>(jsonString);
  }

  public static class SettingsExtensions {
    public static string AsJson(this Settings settings) => JsonUtility.ToJson(settings);
  }
}
