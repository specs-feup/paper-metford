package com.automattic.simplenote.utils;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.text.TextWatcher;
import android.view.View;
import static com.automattic.simplenote.utils.SearchTokenizer.SPACE;
import com.automattic.simplenote.R;
import android.util.AttributeSet;
import com.simperium.client.Bucket;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import android.view.KeyEvent;
import android.text.Editable;
import com.automattic.simplenote.models.Tag;
import android.widget.AdapterView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TagsMultiAutoCompleteTextView extends androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView implements android.widget.AdapterView.OnItemClickListener {
    static final int MUID_STATIC = getMUID();
    private com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> mBucketTag;

    private com.automattic.simplenote.utils.TagsMultiAutoCompleteTextView.OnTagAddedListener mTagAddedListener;

    private android.text.TextWatcher mTextWatcher = new android.text.TextWatcher() {
        @java.lang.Override
        public void afterTextChanged(android.text.Editable s) {
            if (s.length() > 0) {
                setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
            } else {
                setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        }


        @java.lang.Override
        public void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) {
        }


        @java.lang.Override
        public void onTextChanged(java.lang.CharSequence s, int start, int before, int count) {
            if ((count >= 1) && (s.charAt(start) == com.automattic.simplenote.utils.SearchTokenizer.SPACE)) {
                saveTagOrShowError(s.toString());
            }
        }

    };

    public interface OnTagAddedListener {
        void onTagAdded(java.lang.String tag);

    }

    public TagsMultiAutoCompleteTextView(android.content.Context context) {
        super(context);
        init();
    }


    public TagsMultiAutoCompleteTextView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public TagsMultiAutoCompleteTextView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    @java.lang.Override
    public boolean dispatchKeyEvent(android.view.KeyEvent event) {
        if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER) {
            saveTagOrShowError(getText().toString());
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }


    @java.lang.Override
    public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
        notifyTagsChanged();
    }


    public void init() {
        setOnItemClickListener(this);
        addTextChangedListener(mTextWatcher);
    }


    public void notifyTagsChanged() {
        java.lang.String lexical;
        lexical = getText().toString().trim();
        java.lang.String canonical;
        canonical = com.automattic.simplenote.utils.TagUtils.getCanonicalFromLexical(mBucketTag, lexical);
        notifyTagsChanged(canonical);
    }


    public void notifyTagsChanged(java.lang.String tag) {
        if (mTagAddedListener != null) {
            mTagAddedListener.onTagAdded(tag);
        }
    }


    private void saveTagOrShowError(java.lang.String text) {
        if (com.automattic.simplenote.utils.TagUtils.hashTagValid(text)) {
            notifyTagsChanged();
        } else {
            removeTextChangedListener(mTextWatcher);
            setText(getText().toString().trim());
            setSelection(getText().length());
            addTextChangedListener(mTextWatcher);
            android.content.Context context;
            context = getContext();
            com.automattic.simplenote.utils.DialogUtils.showDialogWithEmail(context, context.getString(com.automattic.simplenote.R.string.rename_tag_message_length));
        }
    }


    public void setBucketTag(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket) {
        mBucketTag = bucket;
    }


    public void setOnTagAddedListener(com.automattic.simplenote.utils.TagsMultiAutoCompleteTextView.OnTagAddedListener listener) {
        mTagAddedListener = listener;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
