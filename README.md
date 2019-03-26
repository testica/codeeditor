# Code Editor
[ ![Download](https://api.bintray.com/packages/testica-android/maven/codeeditor/images/download.svg) ](https://bintray.com/testica-android/maven/codeeditor/_latestVersion)

Code Editor is an Android library that simplify the display of code, making easy syntax highlighting and showing number of lines.

<br/>

<div style="background: white; width: 100%;" align="center">
    <img src="cover.png" width="500"/>
</div>

## Requirements
- minSdkVersion 15
- compileSdkVersion 28

## Download

Include into the build.gradle file:

```groovy
dependencies {
    implementation 'me.testica:codeeditor:1.0.1'
}
```
## Usage

From layout:
```xml
<me.testica.codeeditor.Editor
            android:id="@+id/editor"
            app:textSize="16sp"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
```

Then, manipulate programmatically:

Java:
```java
Editor editor = (Editor) findViewById(R.id.editor);
editor.setText("Hello Android");
```
Kotlin:
```kotlin
editor.setText("Hello Android")
```

### Syntax Highlight Rules

Define a list of rules using a regular expression and color

Java:
```java
editor.setSyntaxHighlightRules(
        new SyntaxHighlightRule("[0-9]*", "#00838f"),
        new SyntaxHighlightRule("/\\\\*(?:.|[\\\\n\\\\r])*?\\\\*/|(?<!:)//.*", "#9ea7aa")
);
```
Kotlin:
```kotlin
editor.setSyntaxHighlightRules(
        SyntaxHighlightRule("[0-9]*", "#00838f"),
        SyntaxHighlightRule("/\\\\*(?:.|[\\\\n\\\\r])*?\\\\*/|(?<!:)//.*", "#9ea7aa")
)
```
**Keep in mind that lasts rules will overwrite the previous ones, so order is important.**

### Customization

From **xml** we can set:
- `text`: text code as string
- `textSize`: text dimension of text including number lines
- `fontFamily`: typeface of text including number lines

The same way we can set and get above attributes programmatically:
- `setText(String)`
- `setTextSize(Float)`
- `setTypeface(Typeface)`

Is it possible to customize the number lines or the code text separately? **Yes!**
Programmatically we can get both widget reference and manipulate them:

- `getEditText()`: returns an `EditText` superclass, with this we can handle the code view.
- `getNumLinesView()`: returns a `TextView` superclass, with this we can handle the number lines view.

Below an example changing the background color of number lines view and applying some padding to code view:

Java:
```java
// changing text color and background color to number lines view
editor.getNumLinesView().setBackgroundColor(Color.BLACK);
editor.getNumLinesView().setTextColor(Color.WHITE);

// applying left padding to code view
editor.getEditText().setPadding(10, 0, 0, 0);
```

Kotlin:
```kotlin
// changing text color and background color to number lines view
editor.getNumLinesView().apply { 
    setBackgroundColor(Color.BLACK)
    setTextColor(Color.WHITE)
}

// applying left padding to code view
editor.getEditText().setPadding(10, 0, 0, 0)
```

You can see another approach inside **codeeditorexample/MainActivity** class.

## Contribute

I accept pull requests to fix, improve performance or well-situated feature! Feel free to submit one.

## License MIT
```
MIT License

Copyright (c) 2019 Leonardo Testa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```