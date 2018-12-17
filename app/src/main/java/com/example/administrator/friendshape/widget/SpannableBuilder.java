package com.example.administrator.friendshape.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class SpannableBuilder {
    private Context context;
    private List<SpanWrapper> list;
    private StyleSpan styleSpan;

    private SpannableBuilder(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public SpannableBuilder append(String text, int textSize, int textColor) {
        list.add(new SpanWrapper(text, textSize, textColor));
        return this;
    }

    public SpannableBuilder appendTypeface(String text, int textSize, int textColor, int typeFace) {
        list.add(new SpanWrapper(text, textSize, textColor, typeFace));
        return this;
    }

    public Spannable build() {
        SpannableStringBuilder textSpan = new SpannableStringBuilder();
        int start = 0;
        int end = 0;
        for (int i = 0; i < list.size(); i++) {
            SpanWrapper wrapper = list.get(i);
            String text = wrapper.getText();
            start = end;
            end = end + text.length();
            textSpan.append(text);
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(getContext().getResources().getDimensionPixelSize(wrapper.getTextSize()));
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(wrapper.getTextColor()));
            textSpan.setSpan(sizeSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(colorSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return textSpan;
    }

    public Spannable buildFace() {
        SpannableStringBuilder textSpan = new SpannableStringBuilder();
        int start = 0;
        int end = 0;
        for (int i = 0; i < list.size(); i++) {
            SpanWrapper wrapper = list.get(i);
            String text = wrapper.getText();
            start = end;
            end = end + text.length();
            textSpan.append(text);
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(getContext().getResources().getDimensionPixelSize(wrapper.getTextSize()));
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(wrapper.getTextColor()));
            StyleSpan styleSpan = new StyleSpan(Typeface.defaultFromStyle(wrapper.getTypeFace()).getStyle());
            textSpan.setSpan(sizeSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(colorSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(styleSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE );
        }
        return textSpan;
    }

    public static SpannableBuilder create(Context context) {
        return new SpannableBuilder(context);
    }

    public Context getContext() {
        return context;
    }

    private class SpanWrapper {
        String text;
        int textSize;
        int textColor;
        int typeFace;

        public SpanWrapper(String text, int textSize, int textColor) {
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
        }

        public SpanWrapper(String text, int textSize, int textColor, int typeFace) {
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
            this.typeFace = typeFace;
        }

        public int getTypeFace() {
            return typeFace;
        }

        public void setTypeFace(int typeFace) {
            this.typeFace = typeFace;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }
    }

    //    SpannableStringBuilder spannableString = new SpannableStringBuilder();
//    spannableString.append("暗影IV已经开始暴走了");
//    //setSpan可多次使用
//    StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);//粗体
//    spannableString.setSpan(styleSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//    StyleSpan styleSpan2 = new StyleSpan(Typeface.ITALIC);//斜体
//    spannableString.setSpan(styleSpan2, 3, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//    StyleSpan styleSpan3 = new StyleSpan(Typeface.BOLD_ITALIC);//粗斜体
//    spannableString.setSpan(styleSpan3, 6, 9, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//    ((TextView)findViewById(R.id.mode5)).setText(spannableString);
    //setSpan可多次使用
//    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，即在指定范围的前面和后面插入新字符都不会应用新样式
//    Spannable.SPAN_EXCLUSIVE_INCLUSIVE ：前面不包括，后面包括。即仅在范围字符的后面插入新字符时会应用新样式
//    Spannable.SPAN_INCLUSIVE_EXCLUSIVE ：前面包括，后面不包括。
//    Spannable.SPAN_INCLUSIVE_INCLUSIVE ：前后都包括。

}
