package com.qhsd.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qhsd.library.R;
import com.qhsd.library.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class CodeEditView extends LinearLayout implements TextWatcher, View.OnClickListener {

    /**
     * 默认输入框数量
     */
    private static final int EditViewNum = 6;
    /**
     * 存储EditText对象
     */
    private ArrayList<TextView> mTextViewsList = new ArrayList<>();
    private Context mContext;
    private EditText mEditText;
    /**
     * 方格边框大小
     */
    private int borderSize = 35;
    /**
     * 方格间距
     */
    private int borderMargin = 10;
    /**
     * 字体大小
     */
    private int textSize = 8;
    /**
     * 字体颜色
     */
    private int textColor = Color.BLACK;

    private InputEndListener callBack;

    public CodeEditView(Context context) {
        this(context, null);
    }

    public CodeEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CodeEditView);
            try {
                borderSize = array.getInteger(R.styleable.CodeEditView_borderSize, borderSize);
                borderMargin = array.getInteger(R.styleable.CodeEditView_borderMargin, borderMargin);
                textSize = array.getInteger(R.styleable.CodeEditView_textSize, textSize);
                textColor = array.getColor(R.styleable.CodeEditView_textColor, textColor);
            } finally {
                array.recycle();
            }
        }

        initEditText();
        initTextView();
    }

    private void initEditText() {
        mEditText = new EditText(mContext);
        mEditText.setBackgroundColor(Color.parseColor("#00000000"));
        mEditText.setMaxLines(1);
        mEditText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(EditViewNum)});
        mEditText.addTextChangedListener(this);
        mEditText.setTextSize(0);
        addView(mEditText);
    }

    private void initTextView() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(VERTICAL);
        LinearLayout linearLayout1 = new LinearLayout(mContext);
        LinearLayout linearLayout2 = new LinearLayout(mContext);
        //设置方格间距
        LayoutParams params = new LayoutParams(
                ScreenUtils.dp2px(mContext, borderSize), ScreenUtils.dp2px(mContext, borderSize));
        params.setMargins(ScreenUtils.dp2px(mContext, borderMargin), 0, 0, 0);
        LayoutParams params2 = new LayoutParams(
                ScreenUtils.dp2px(mContext, borderSize), ScreenUtils.dp2px(mContext, 1));
        params2.setMargins(ScreenUtils.dp2px(mContext, borderMargin), 0, 0, 0);
        //设置方格文字
        for (int i = 0; i < EditViewNum; i++) {
            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(ScreenUtils.sp2px(mContext, textSize));
            textView.getPaint().setFakeBoldText(true);
            textView.setLayoutParams(params);
            textView.setInputType(InputType.TYPE_CLASS_NUMBER);
            textView.setTextColor(textColor);
            textView.setOnClickListener(this);
            mTextViewsList.add(textView);
            linearLayout1.addView(textView);
            View liner = new View(mContext);
            liner.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_9));
            liner.setLayoutParams(params2);
            linearLayout2.addView(liner);
        }
        linearLayout.addView(linearLayout1);
        linearLayout.addView(linearLayout2);
        addView(linearLayout);
        //显示隐藏软键盘
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                mEditText.setHintTextColor(Color.parseColor("#ff0000"));
            }
        }, 500);
        //监听删除键
        mEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (mEditText.getText().length() >= mTextViewsList.size()) {
                        return false;
                    }
                    mTextViewsList.get(mEditText.getText().length()).setText("");
                }
                return false;
            }
        });
    }

    public void callItem0Click(){
        mTextViewsList.get(0).callOnClick();
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    public void clearText() {
        mEditText.setText("");
        for (TextView textView : mTextViewsList) {
            textView.setText("");
        }
    }

    public void setOnInputEndCallBack(InputEndListener onInputEndCallBack) {
        callBack = onInputEndCallBack;
    }

    /**
     * 输入监听
     */
    public interface InputEndListener {
        /**
         * 文字改变
         *
         * @param text
         */
        void afterTextChanged(String text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (callBack != null) {
            callBack.afterTextChanged(s.toString());
        }
        if (s.length() <= 1) {
            mTextViewsList.get(0).setText(s);
        } else {
            mTextViewsList.get(mEditText.getText().length() - 1).setText(s.subSequence(s.length() - 1, s.length()));
        }
    }

    @Override
    public void onClick(View v) {
        // TextView点击时获取焦点弹出输入法
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
