[![GitHub Repo stars](https://img.shields.io/github/stars/ThothDroid/MAAT?style=for-the-badge&logo=github&color=yellowgreen)](https://github.com/ThothDroid/MAAT)
[![Static part of Badge](https://img.shields.io/badge/Part%20of-Egyptian%20Writer%20App-%233DDC84?style=for-the-badge&logo=android&logoColor=white)](https://github.com/ThothDroid/Egyptian_Writer/)
[![GitHub License](https://img.shields.io/github/license/ThothDroid/MAAT?style=for-the-badge&logo=gnu&color=yellow)](https://github.com/ThothDroid/MAAT?tab=GPL-3.0-1-ov-file)
[![GitHub forks](https://img.shields.io/github/forks/ThothDroid/MAAT?style=for-the-badge&logo=git&logoColor=white&color=%23F05032)](https://github.com/ThothDroid/MAAT)
\
[![jitpack](https://jitpack.io/v/ThothDroid/MAAT.svg)](https://jitpack.io/#ThothDroid/MAAT)
[![Static wiki Badge](https://img.shields.io/badge/Egyptian%20Writer-WIKI-yellow?style=flat&logo=gitbook&logoColor=white)](https://github.com/ThothDroid/Egyptian_Writer/wiki)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/ThothDroid/MAAT?color=blue)](https://github.com/ThothDroid/MAAT)
[![GitHub Release](https://img.shields.io/github/v/release/ThothDroid/MAAT?color=%23F05032)](https://github.com/ThothDroid/MAAT/releases/latest)

![Feature Graphic](Feature_Graphic.png)

# Mobile API for Ancient Texts (MAAT)
A library for rendering Egyptian hieroglyphic texts by using the `GlyphX` format. \
\
*This library is part of the [Egyptian Writer](https://github.com/ThothDroid/Egyptian_Writer) Android App.*

## Disclaimer
This library only handles the calculation of the position and scale of each hieroglyph. The rendering and storing of the images are not included. Besides the library uses the `GlyphX` code for encoding the hieroglyphic texts.
<br/><br/>
A library for converting `GlyphX` to `MdC` and back is stored here: [GlyphConverter](https://github.com/ThothDroid/GlyphConverter).

> [!TIP]
> **You don't want to handle all the images by yourself?
> Try the [THOTH](https://github.com/ThothDroid/THOTH) library which uses the MAAT library and handles all the images.**\
> It contains an android custom view which renders everything for you. You only have to adjust a few parameters!

## Implementation with jitpack
Add this to your `settings.gradle.kts` at the end of repositories:
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
  }
}
```
Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation("com.github.ThothDroid:MAAT:1.6.0")
}
```
> [!NOTE]
> For the implementation for other build systems like `Groovy` see [here](https://jitpack.io/#ThothDroid/MAAT/)

## Implementation with `.aar` file
Download the [`MAAT_versionname.aar`](https://github.com/ThothDroid/MAAT/releases/latest) file from the latest release, create a `libs` folder in your project directory and paste the file there. Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation(files("../libs/MAAT_versionname.aar"))
}
```

> [!IMPORTANT]
> If you renamed the `.aar` file you also have to change the name in the dependencies

## Usage
### Step 1
First add this code to initialize the Maat Library using the `GlyphX` document.
```
String GlyphXContent = "";  // Save your GlyphX string here

// Convert your string to an XML Document

DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();                      // Parser that produces DOM object trees from XML content
DocumentBuilder builder;                                                                    // API to obtain DOM Document instance
builder = factory.newDocumentBuilder();                                                     // Create DocumentBuilder with default configuration
Document GlyphXDocument = builder.parse(new InputSource(new StringReader(GlyphXContent)));  // Parse the content to Document object

BoundCalculation boundCalculation = new BoundCalculation(GlyphXDocument);                   // Create an object of the BoundCalculation class
```
### Step 2 (optional)
Now, you can optionally get all ids used in the `GlyphX` document. This could be useful for analytics or caching.
<br>**You can [skip this step](#step-3), if you only want to get the bounds of the signs.**

You can get the ids using this code:
```
ArrayList<String> ids = boundCalculation.getIds(false, false);    // Get the ids of all the hieroglyphs e.g. ["sn","n","nw","w","A1"]
```
**The method to get the ids has two parameters:**<br/>
`getIds(boolean lines, boolean rtl)`<br/>
The first parameter is the `writingLayout`:
- `lines`: `true`
- `columns`: `false`

The second parameter is the `writingDirection`:
- `RTL`: `true`
- `LTR`: `false`
> [!IMPORTANT]
> The parameters have to be the same as used in the `BoundProperty`!

The ids are either codes from [Gardiner's Sign List](https://ancientegyptonline.co.uk/Gardiner-sign-list/) like `A1`, `Z1`, `A1B` or `O34` or their [phonetic alternates](http://71.174.62.16/image/WebGlyph/SmallCollection.pdf) like `sn`, `zA`, `xAst` or `ra`.
### Step 3

In the next step, you have to set up the `BoundProperty`. This class is included in the library and there you store all the settings for the library.
```
BoundProperty property = new BoundProperty(x, y, textSize,
                            verticalOrientation, writingDirection, writingLayout,
                            drawLines, lineThickness, pagePaddingLeft,
                            pagePaddingTop, pagePaddingRight,
                            pagePaddingBottom, signPadding,
                            layoutSignPadding, interLinePadding);
```
Here is the explanation for the parameters:

- `x` This value defines the global x-position for the whole text. With help of this value, you can move the text as a whole.
- `y` This value defines the global y-position for the whole text. With help of this value, you can move the text as a whole.
- `textSize` This defines the height of a line in pixels. If the `writingLayout` is set to `WRITING_LAYOUT_COLUMNS` this value defines the width of a column in pixels.
- `verticalOrientation` This parameter can only have three values and defines the vertical position of smaller signs (like `n`):
    - `BoundProperty.VERTICAL_ORIENTATION_UP` Put signs to the top of the line
    - `BoundProperty.VERTICAL_ORIENTATION_MIDDLE` Center signs vertically
    - `BoundProperty.VERTICAL_ORIENTATION_DOWN` Drop signs on Baseline
- `writingDirection` This parameter can only have two values and defines the direction of writing:
    - `BoundProperty.WRITING_DIRECTION_LTR` Write from left to right
    - `BoundProperty.WRITING_DIRECTION_RTL` Write from right to left
- `writingLayout` This parameter also have two possible values and determines if signs should be written in lines or in columns:
    - `BoundProperty.WRITING_LAYOUT_LINES` Write signs in lines
    - `BoundProperty.WRITING_LAYOUT_COLUMNS` Write signs in columns
- `drawLines` This determines whether to draw strokes between the lines / columns
- `lineThinkness` This defines the thickness of the stroke if it's enabled
- `paddings`:
  - `pagePadding` This value is separated into `pagePaddingLeft`, `pagePaddingTop`, `pagePaddingRight` and `pagePaddingBottom` and defines the padding of the text as a whole
  - `signPadding` This value defines the padding between two signs which are not grouped
  - `layoutSignPadding` This value defines the relative space between two signs which are in the same group
  - `interLinePadding` This is the space between the lines / columns of the text

### Step 4
After that, it is time to set up the `MaatAdapter`. The `MaatAdapter` serves as an adapter between the `Maat` library and your code. As the `Maat` library doesn't have direct access to the images, you have to perform architectural specific operations by yourself. To get the required data, and pass it back, you have to use the `MaatAdapter`.

Enough theory, let's get started!

The `MaatAdapter` is an Interface, featuring functions for all operations needed _(By now, only one)_. So create a class, which implements this Interface. In my case, an abstract class:

```
MaatAdapter maatAdapter = new MaatAdapter() {
    @Override
    public ValuePair<Float, Float> measureSignDimension(String id) {
        float width = 0f;
        float height = 0f;
        
        // Put your code to get the dimensions of a Sign here
        // assign real values to width and height variables
        
        return new ValuePair<>(width, height);
    }
};
```

### Step 5
Finally get the calculated bounds:
```
ArrayList<Part> parts = boundCalculation.getBounds(property, maatAdapter);
```
You get an `ArrayList` of `Part`-objects, each object representing a part of the text. Now you can loop through the `ArrayList` and render every part.
<br>Currently, there are these types of parts:
- `SignPart` represents a single Hieroglyph. It contains a String (`getId()`) which stores the id of the sign and a `Rect` (`getBound()`), which stores the position and scale value


That's it!<br>

## Supported Codes
The MAAT library supports the following codes:
- `<sign id="X"/>` A single sign. The id is the code of the hieroglyph. The library supports both Gardiner's Sign List and their phonetic alternates.
- `<v></v>` Items inside are arranged vertically
- `<h></h>` Items inside are arranged horizontally
- `<br/>` A line break
- `<pbr/>` A page break
- `<space/>` Acts as a space between signs. The width and height of the space is equal to half of the `textSize` value in the `BoundProperty`.
- `<gap/>` The same as `<space/>` but with a width and height equal to the `textSize` value in the `BoundProperty`.

## Version Catalog
> [!IMPORTANT]
> Since version `12.08.2026@1.6.0` the version catalog uses the new versioning system [see here](https://medium.com/@wassimsakri/the-ultimate-guide-to-versioning-in-software-development-e846eb292a0d).
### 13.09.2025@1.0.0
This is the first release of the MAAT library.
### 06.12.2025@1.5.0
- Support for `<br/>` and `<pbr/>` tags added
- Issue with `RTL` layout solved
- Paddings for graphical purposes added
### 07.12.2025@1.5.1
- dependencies upgraded
- Minor bug fixes related to `RTL` layout.
### 12.08.2026@1.6.0
- dependency upgrades
- Fixed bug related to `RTL` layout
- Support for `space` and `gap` tags added
### latest Version
`12.08.2026@1.6.0`
