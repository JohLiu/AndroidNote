package com.joh.androidnote;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TextView富文本使用
 */
public class TextViewActivity extends AppCompatActivity {

    private static final String TAG = "TextViewActivity";

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv11)
    TextView tv11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        ButterKnife.bind(this);

        ///////////////////////////////////////////////////////////////////////////////

        initHtml();
        initSpannableString();
    }

    ///////////////////////////////////////////////////////////////////////////////

    /**
     * html使用方式（以下标签可以合并使用）：
     * · 字体颜色设置：<font color='#ff0000'>字体</font>
     * · 换行：<br>
     * · 下划线：<u>字体</u>  删除线：<s>字体</s>  斜体字：<i>字体</i>  粗体：<b>字体</b>
     * · 字体大小设置：<big>字体</big>  或者  <small>字体</small>  //需要注意，字体大小没有固定值设置，只是在当前字体大小基础上变大或变小，不过可叠加使用
     * · 段落：<p>段落内容</p>
     * · 链接：<a></a>
     * · 图片：<image/>
     */
    private void initHtml() {
        // 字体样式标签使用
        String txt1 = "1.设置字体颜色<font color='red'>字体颜色改变部分1</font>和<font color='#FF8000'>字体颜色改变部分2</font>" +
                "<br>2.<font color='#FF8000'><u> 下划线 </u></font>、<s>删除线</s>、<i> 斜体 </i>和<b> 粗体 </b>";
        /**
         * Api24以上需要传入flags，24之前不需要传。
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
        String txt4 = "5.测试地址信息 <a href='https://github.com/alibaba/ARouter'>【阿里出品】帮助 Android App 进行组件化改造的路由框架</a>";
        // 设置后点击超链接会向外部跳转
        tv4.setMovementMethod(LinkMovementMethod.getInstance());
        tv4.setText(Html.fromHtml(txt4));

    }

    ///////////////////////////////////////////////////////////////////////////////

    /**
     * SpannableString使用方式：
     * flags值含义（这里的包括指的是start、end的值）
     * · Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 前后都不包括
     * · Spanned.SPAN_EXCLUSIVE_INCLUSIVE 前面不包括，后面包括
     * · Spanned.SPAN_INCLUSIVE_EXCLUSIVE 前面包括，后面不包括
     * · Spanned.SPAN_INCLUSIVE_INCLUSIVE 前后都包括
     */
    private void initSpannableString() {
        // 字体颜色、背景
        SpannableString ts1 = new SpannableString("1.设置字体颜色改变部分1改变部分2背景色");
        ts1.setSpan(new ForegroundColorSpan(Color.RED),
                8, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ts1.setSpan(new ForegroundColorSpan(Color.BLUE),
                13, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ts1.setSpan(new BackgroundColorSpan(Color.RED),
                18, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv5.setText(ts1);

        // 下划线、删除线、斜体和粗体
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

        // 上标、下标
        SpannableString ts3 = new SpannableString("3.上标1、下标2");
        ts3.setSpan(new SuperscriptSpan(), 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ts3.setSpan(new SubscriptSpan(), 8, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv7.setText(ts3);

        //设置字体大小（绝对值,单位：像素）
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

        // 电话、网络、短信(使用sms:或者smsto:)
        SpannableString ts5 = new SpannableString("5.打电话\n百度地址\n短信发送");
        ts5.setSpan(new URLSpan("tel:123456789"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ts5.setSpan(new URLSpan("http://www.baidu.com"), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ts5.setSpan(new URLSpan("sms:123456789"), 9, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置可点击
        tv9.setMovementMethod(LinkMovementMethod.getInstance());
        tv9.setText(ts5);

        // 图片
        SpannableString ts6 = new SpannableString("6.在文本中添加表情（表情）");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan = new ImageSpan(drawable);
        ts6.setSpan(imageSpan, 8, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv10.setText(ts6);

        // 点击事件
        SpannableString ts7 = new SpannableString("请仔细阅读相关协议，若注册软件，代表你已同意注册协议和隐私政策");
        //设置点击事件
        ts7.setSpan(new Clickable(registerClickListener), 22, 26,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ts7.setSpan(new Clickable(privacyListener), 27, 31,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv11.setText(ts7);
        tv11.setMovementMethod(LinkMovementMethod.getInstance());
    }

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

}
