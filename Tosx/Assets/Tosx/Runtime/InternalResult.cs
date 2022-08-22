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

using System;
using UnityEngine;

namespace Tosx {
  public enum ResultActionType {
    Accept,
    Dismiss,
    TosClick,
    PpClick
  }

  [Serializable]
  public partial class InternalResult {
    [SerializeField]
    private string result;

    public InternalResult(string result) => this.result = result;

    public string Result => result;
  }

  public partial class InternalResult {
    public static InternalResult FromJson(string jsonString) => JsonUtility.FromJson<InternalResult>(jsonString);

    public static InternalResult FromActionType(ResultActionType actionType) => actionType switch {
        ResultActionType.Accept => new InternalResult("accept"),
        ResultActionType.Dismiss => new InternalResult("dismiss"),
        ResultActionType.TosClick => new InternalResult("tos_click"),
        ResultActionType.PpClick => new InternalResult("pp_click"),
        _ => throw new ArgumentOutOfRangeException(nameof(actionType), actionType, null)
    };
  }

  public static class InternalResultExtensions {
    public static string AsJson(this InternalResult result) => JsonUtility.ToJson(result);

    public static ResultActionType AsActionType(this InternalResult result) => result.Result switch {
        "accept" => ResultActionType.Accept,
        "dismiss" => ResultActionType.Dismiss,
        "tos_click" => ResultActionType.TosClick,
        "pp_click" => ResultActionType.PpClick,
        _ => throw new ArgumentOutOfRangeException()
    };
  }
}
