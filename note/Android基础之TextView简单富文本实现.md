# Android基础之TextView简单富文本实现

标签： Android基础

---
### 1.Html.fromHtml()
`Html.fromHtml()`是Android提供的简单支持部分html标签的方法。
查看`Html`源码可知目前支持的html标签有`<br>`、`<p>`、`<font>`、`<a>`、`<big>`等
```java
private void handleStartTag(String tag, Attributes attributes) {
    if (tag.equalsIgnoreCase("br")) {
        // We don't need to handle this. TagSoup will ensure that there's a </br> for each <br>
        // so we can safely emit the linebreaks when we handle the close tag.
    } else if (tag.equalsIgnoreCase("p")) {
        startBlockElement(mSpannableStringBuilder, attributes, getMarginParagraph());
        startCssStyle(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("ul")) {
        startBlockElement(mSpannableStringBuilder, attributes, getMarginList());
    } else if (tag.equalsIgnoreCase("li")) {
        startLi(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("div")) {
        startBlockElement(mSpannableStringBuilder, attributes, getMarginDiv());
    } else if (tag.equalsIgnoreCase("span")) {
        startCssStyle(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("strong")) {
        start(mSpannableStringBuilder, new Bold ());
    } else if (tag.equalsIgnoreCase("b")) {
        start(mSpannableStringBuilder, new Bold ());
    } else if (tag.equalsIgnoreCase("em")) {
        start(mSpannableStringBuilder, new Italic ());
    } else if (tag.equalsIgnoreCase("cite")) {
        start(mSpannableStringBuilder, new Italic ());
    } else if (tag.equalsIgnoreCase("dfn")) {
        start(mSpannableStringBuilder, new Italic ());
    } else if (tag.equalsIgnoreCase("i")) {
        start(mSpannableStringBuilder, new Italic ());
    } else if (tag.equalsIgnoreCase("big")) {
        start(mSpannableStringBuilder, new Big ());
    } else if (tag.equalsIgnoreCase("small")) {
        start(mSpannableStringBuilder, new Small ());
    } else if (tag.equalsIgnoreCase("font")) {
        startFont(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("blockquote")) {
        startBlockquote(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("tt")) {
        start(mSpannableStringBuilder, new Monospace ());
    } else if (tag.equalsIgnoreCase("a")) {
        startA(mSpannableStringBuilder, attributes);
    } else if (tag.equalsIgnoreCase("u")) {
        start(mSpannableStringBuilder, new Underline ());
    } else if (tag.equalsIgnoreCase("del")) {
        start(mSpannableStringBuilder, new Strikethrough ());
    } else if (tag.equalsIgnoreCase("s")) {
        start(mSpannableStringBuilder, new Strikethrough ());
    } else if (tag.equalsIgnoreCase("strike")) {
        start(mSpannableStringBuilder, new Strikethrough ());
    } else if (tag.equalsIgnoreCase("sup")) {
        start(mSpannableStringBuilder, new Super ());
    } else if (tag.equalsIgnoreCase("sub")) {
        start(mSpannableStringBuilder, new Sub ());
    } else if (tag.length() == 2 &&
            Character.toLowerCase(tag.charAt(0)) == 'h' &&
            tag.charAt(1) >= '1' && tag.charAt(1) <= '6') {
        startHeading(mSpannableStringBuilder, attributes, tag.charAt(1) - '1');
    } else if (tag.equalsIgnoreCase("img")) {
        startImg(mSpannableStringBuilder, attributes, mImageGetter);
    } else if (mTagHandler != null) {
        mTagHandler.handleTag(true, tag, mSpannableStringBuilder, mReader);
    }
}
```
使用也比较简单，示例如下：
```java
// 字体样式标签使用
String txt1 = "1.设置字体颜色<font color='red'>字体颜色改变部分1</font>和<font color='#FF8000'>字体颜色改变部分2</font>" +
"<br>2.下划线 <font color='#FF8000'><u> 下划线 </u></font>、<s>删除线</s>、<i> 斜体 </i>和<b> 粗体 </b>";
/**
 * Api24以上需要传入flags，24之前不需要传。
 */
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
    tv1.setText(Html.fromHtml(txt1, Html.FROM_HTML_MODE_LEGACY));
} else {
    tv1.setText(Html.fromHtml(txt1));
}

// 字体规格标签使用
String txt2 = "3.改变字体大小 <font color='#FF8000'><big>字体变<big>大部分</big></big></font> 和 <small>字体变小部分</small>";
tv2.setText(Html.fromHtml(txt2));

// 段落标签使用
String txt3 = "4.段落使用展示<p>段落内容展示一</P><p>段落内容展示二</P><p>段落内容展示三</P>";
tv3.setText(Html.fromHtml(txt3));

// 超链接标签使用
String txt4 = "4.测试地址信息 <a href='https://github.com/alibaba/ARouter'>【阿里出品】帮助 Android App 进行组件化改造的路由框架</a>";
// 设置后点击超链接会向外部跳转
tv4.setMovementMethod(LinkMovementMethod.getInstance());
tv4.setText(Html.fromHtml(txt4));
```
### 2.SpannableString 
`Html.fromHtml()`虽然可以实现简单富文本的展示，但是支持的html标签有限，所以我们还可以使用`SpannableString`来实现更多富文本的展示方式。

- 字体颜色、背景
	```java
	SpannableString ts1 = new SpannableString("1.设置字体颜色改变部分1改变部分2背景色");
	ts1.setSpan(new ForegroundColorSpan(Color.RED),
	8, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts1.setSpan(new ForegroundColorSpan(Color.BLUE),
	13, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts1.setSpan(new BackgroundColorSpan(Color.RED),
	18, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	tv5.setText(ts1);
	```

- 下划线、删除线、斜体和粗体
	```java
	SpannableString ts2 = new SpannableString("2.下划线、删除线、斜体、粗体、粗斜体");
	ts2.setSpan(new UnderlineSpan(),
	2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts2.setSpan(new StrikethroughSpan(),
	6, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts2.setSpan(new StyleSpan(Typeface.ITALIC),
	10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts2.setSpan(new StyleSpan(Typeface.BOLD),
	13, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts2.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),
	16, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	tv6.setText(ts2);
	```
	
- 上标、下标
	```java
	SpannableString ts3 = new SpannableString("3.上标1、下标2");
	ts3.setSpan(new SuperscriptSpan(), 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts3.setSpan(new SubscriptSpan(), 8, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	tv7.setText(ts3);
	```

- 字体大小	
	```java
	 // 第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
	        SpannableString ts4 = new SpannableString("4.设置字体大小、字体大小、字体大小和字体大小");
	        ts4.setSpan(new AbsoluteSizeSpan(20), 4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        ts4.setSpan(new AbsoluteSizeSpan(20, true), 9, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
	        // 0.5f表示默认字体大小的一半
	        // 1.5f表示默认字体大小的两倍
	        ts4.setSpan(new RelativeSizeSpan(0.5f), 14, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        ts4.setSpan(new RelativeSizeSpan(1.5f), 19, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        tv8.setText(ts4);
	```

- 电话、网络、短信
	```java
	SpannableString ts5 = new SpannableString("5.打电话\n百度地址\n短信发送");
	ts5.setSpan(new URLSpan("tel:123456789"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts5.setSpan(new URLSpan("http://www.baidu.com"), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts5.setSpan(new URLSpan("sms:123456789"), 9, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	// 设置可点击
	tv9.setMovementMethod(LinkMovementMethod.getInstance());
	tv9.setText(ts5);
	```

- 图片
	```java
	SpannableString ts6 = new SpannableString("6.在文本中添加表情（表情）");
	Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
	drawable.setBounds(0, 0, 42, 42);
	ImageSpan imageSpan = new ImageSpan(drawable);
	ts6.setSpan(imageSpan, 8, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
	tv10.setText(ts6);
	```

- 点击事件
	```java
	SpannableString ts7 = new SpannableString("请仔细阅读相关协议，若注册软件，代表你已同意注册协议和隐私政策");
	//设置点击事件
	ts7.setSpan(new Clickable(registerClickListener), 22, 26,
	Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	ts7.setSpan(new Clickable(privacyListener), 27, 31,
	Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	tv11.setText(ts7);
	tv11.setMovementMethod(LinkMovementMethod.getInstance());
	
	private View.OnClickListener registerClickListener = new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	        Toast.makeText(TextViewActivity.this, "注册协议", Toast.LENGTH_SHORT).show();
	    }
	};
	private View.OnClickListener privacyListener = new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	        Toast.makeText(TextViewActivity.this, "隐私政策", Toast.LENGTH_SHORT).show();
	    }
	};
	
	class Clickable extends ClickableSpan {
	    private final View.OnClickListener mListener;
	
	    public Clickable(View.OnClickListener l) {
	        mListener = l;
	    }
	
	    /**
	     * 重写父类点击事件
	     */
	    @Override
	    public void onClick(View v) {
	        mListener.onClick(v);
	    }
	
	    /**
	     * 重写父类updateDrawState方法 我们可以给TextView设置字体颜色,背景颜色等等...
	     */
	    @Override
	    public void updateDrawState(TextPaint ds) {
	        ds.setColor(getResources().getColor(R.color.colorPrimary));
	    }
	}
	```




