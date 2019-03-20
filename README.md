[![Release](https://jitpack.io/v/mahimrocky/TagView.svg)](https://github.com/mahimrocky/TagView/releases/tag/1.0.1)
# TagView

This ibirary help to pick up Text as Tag. Like **Skill**selection or other things what you want. You can call it **Tag with EditText** You can select specicfic text from showing list or from editText text. Easy to us and Simple library

Sample
<p align="center">
  <img src="https://github.com/mahimrocky/TagView/blob/master/screenshot.png" width="200" height="400" />
  <img src="https://github.com/mahimrocky/TagView/blob/master/screenshot_1.png" width="200" height="400" /> 
</p> 

# Root Gradle
```sh
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

# App Gradle:

```sh
dependencies {
	   implementation 'com.github.mahimrocky:TagView:1.0.0'
	   implementation 'com.google.android:flexbox:1.0.0'
	}
```
Ok Now starts **Implementation** part. You have to set just follwoing **xml**
**Note:** No need to add extra Edit text for Tag selection. The xml file will auto provide

```sh
    <com.skyhope.materialtagview.TagView
        android:id="@+id/text_view_show_more"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```

**And now here we wil discuss how we can use different property**

In Activity you can use like:
```sh
    TagView tagView = findViewById(R.id.tag_view_test);
```
User can set **Tag** in two way
1.Typing text and enter **Special Character**
2.Select item from provided list

So if you want to provide String list you can predefine Tag list by using:

```sh
    tagView.addTagSeparator(TagSeparator.AT_SEPARATOR); // @ seprator
```

or in XML you can set

```sh
    app:tag_separator="HASH_SEPARATOR" // Hash seperator
```

**To get Tag add or Remove listener**

```sh
    tagView.initTagListener(TagItemListener listener); // You can implement it
```
**To get selected tag**
```sh
    tagView.getSelectedTags();// taht will return TagModel List
```

There are following property that you can use in **XML** section
| Attributes | Purpose |
| ------ | ------ |
| ```app:tag_text_color```|  To change Tag text color|
| ```app:tag_background_color```|  To change Tag Background color|
| ```app:tag_limit```|  To set how many tag will set|
| ```app:close_icon```|  To set remove button of each tag|
| ```app:limit_error_text```|  To set message if tag reach its limit|
| ```app:tag_separator```|  To set **Special Character** that entering in EditText will create a tag|

**How you can set predefine tag list?**
You can set List of String or array of String
```sh
String[] tagList = new String[]{"Hello1", "Hello2", "Hello3"};
tagView.setTagList(tagList);

```

**The following methods to change property of tag**
| Mtehod Name | Purpose |
| ------ | ------ |
| ```addTagLimit(int limit)```|  To set Tag limit|
| ```setTagBackgroundColor(int color)```|  To set Tag background color|
| ```setTagBackgroundColor(String color)```|  To set Tag background color|
| ```setTagTextColor(int color)```|  To set Tag text color|
| ```setTagTextColor(String color)```| To set Tag text color|
| ```setMaximumTagLimitMessage(String message)```|  To set Tag limit warning message|
| ```setCrossButton(Drawable crossDrawable)```|  To set Tag remove button|
| ```setCrossButton(Bitmap crossBitmap)```|  To set Tag remove button|
| ```setTagList(List<String> tagList)```|  To set Predefine Tag list|
| ```setTagList(String... tagList)```|  To set Predefine Tag list as String array|
| ```getSelectedTags()```|  To get all selected TagList

# Happy Coding
