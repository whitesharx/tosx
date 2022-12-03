# ToSx

Native alerts of Terms of Service, Privacy Policy and GDPR for Unity.

# Install

You can install ToSx as Unity Package using latest stable git tag. Simple
add this line to your Package Manager manifest:


```json
{
  "dependencies": {
    "com.whitesharx.tosx": "https://github.com/whitesharx/tosx.git?path=Tosx/Assets/Tosx#0.5.5"
  }
}

```

# Usage

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
