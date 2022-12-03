<h1 align="center">ToSx</h1>


<h3 align="center">✅ Native alerts of Terms of Service, Privacy Policy for Unity ✅</h3>

<p align="center">
  <a aria-label="License" href="https://github.com/whitesharx/httx/blob/develop/LICENSE.md">
    <img alt="" src="https://img.shields.io/static/v1?label=LICENSE&message=MIT&style=for-the-badge&labelColor=000000&color=blue">
  </a>

<br>

* Displays only *single* button and embedded urls. 
* User can't close this alert, only accept it or close app.
* You need **Unity 2019.x** or newer

<p align="center">
  <img src=".docs/example-apple.jpeg" width="200px"/><img src=".docs/example-android.jpeg" width="200px"/>
</p>

# Install ToSx with Package Manger 

ToSx distributed as standard [Unity Package](https://docs.unity3d.com/Manual/PackagesList.html)
You can install this package using Unity Package Manager, just add the
following to your `Packages/manifest.json`:


```json
{
  "dependencies": {
    "com.whitesharx.tosx": "https://github.com/whitesharx/tosx.git?path=Tosx/Assets/Tosx#0.5.5"
  }
}

```

# How to Use

Whole source code of [example](https://github.com/whitesharx/tosx/blob/develop/Tosx/Assets/Tosx/Scenes/SandboxBehaviour.cs) can be found inside Sandbox scene.

```csharp

var settings = new DisplaySettings(title, message, tosText, ppText, tosUrl, ppUrl, actionTitle);
using var toSx = new TosxAlert(settings);

await toSx.DisplayAsync();
```

# License

Httx is available under the [MIT](https://en.wikipedia.org/wiki/MIT_License) license.

<p align="center">
  Made with 🖤 at <a aria-label="WhiteSharx" href="https://whitesharx.com">WhiteSharx</a>
</p>
