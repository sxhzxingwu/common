package com.android.weici.common.widget.diaolog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.TransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.weici.common.R;
import com.android.weici.common.Tools;
import com.android.weici.common.widget.help.VUIResHelper;

/**
 * Created by Mouse on 2017/11/16.
 */

public class VUIDialog extends Dialog{

    public VUIDialog(Context context) {
        this(context, R.style.VUI_Dialog);
    }

    public VUIDialog(Context context, int styleRes) {
        super(context, styleRes);
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWidth();
    }

    private void initDialogWidth() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        window.setDimAmount(0.6f); // 部分刷机会导致背景透明，这里保证一次
        WindowManager.LayoutParams wmlp = window.getAttributes();
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wmlp);
    }

    /**
     * 带输入框的对话框 Builder
     */
    public static class EditTextDialogBuilder extends VUIDialogBuilder<EditTextDialogBuilder> {
        protected String mPlaceholder;
        protected TransformationMethod mTransformationMethod;
        protected RelativeLayout mMainLayout;
        protected EditText mEditText;
        protected ImageView mRightImageView;
        private int mInputType = InputType.TYPE_CLASS_TEXT;

        public EditTextDialogBuilder(Context context) {
            super(context);
            mEditText = new EditText(mContext);
            mEditText.setHintTextColor(ContextCompat.getColor(context,R.color.vui_config_color_gray_3));
            mEditText.setTextColor(ContextCompat.getColor(context,R.color.vui_config_color_black));
            mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, Tools.dip2px(context,16));
            mEditText.setFocusable(true);
            mEditText.setFocusableInTouchMode(true);
            mEditText.setImeOptions(EditorInfo.IME_ACTION_GO);
            mEditText.setGravity(Gravity.CENTER_VERTICAL);
            mEditText.setId(R.id.vui_dialog_edit_input);

            mRightImageView = new ImageView(mContext);
            mRightImageView.setId(R.id.vui_dialog_edit_right_icon);
            mRightImageView.setVisibility(View.GONE);
        }

        /**
         * 设置输入框的 placeholder
         */
        public EditTextDialogBuilder setPlaceholder(String placeholder) {
            this.mPlaceholder = placeholder;
            return this;
        }

        /**
         * 设置输入框的 placeholder
         */
        public EditTextDialogBuilder setPlaceholder(int resId) {
            return setPlaceholder(mContext.getResources().getString(resId));
        }

        /**
         * 设置 EditText 的 transformationMethod
         */
        public EditTextDialogBuilder setTransformationMethod(TransformationMethod transformationMethod) {
            mTransformationMethod = transformationMethod;
            return this;
        }

        /**
         * 设置 EditText 的 inputType
         */
        public EditTextDialogBuilder setInputType(int inputType) {
            mInputType = inputType;
            return this;
        }

        @Override
        protected void onCreateContent(VUIDialog dialog, ViewGroup parent) {
            mMainLayout = new RelativeLayout(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = VUIResHelper.getAttrDimen(mContext, hasTitle() ? R.attr.vui_dialog_edit_content_padding_top : R.attr.vui_dialog_content_padding_top_when_no_title);
            lp.leftMargin = VUIResHelper.getAttrDimen(mContext, R.attr.vui_dialog_padding_horizontal);
            lp.rightMargin = VUIResHelper.getAttrDimen(mContext, R.attr.vui_dialog_padding_horizontal);
            lp.bottomMargin = VUIResHelper.getAttrDimen(mContext, R.attr.vui_dialog_edit_content_padding_bottom);
            mMainLayout.setBackgroundDrawable(VUIResHelper.getAttrDrawable(mContext,R.attr.vui_dialog_edittext_background));
            mMainLayout.setLayoutParams(lp);

            if(mTransformationMethod!=null){
                mEditText.setTransformationMethod(mTransformationMethod);
            }else{
                mEditText.setInputType(mInputType);
            }

            mEditText.setBackgroundResource(0);
            mEditText.setPadding(0, 0, 0, Tools.dip2px(mContext,5));
            RelativeLayout.LayoutParams editLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editLp.addRule(RelativeLayout.LEFT_OF, mRightImageView.getId());
            editLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            if (mPlaceholder != null) {
                mEditText.setHint(mPlaceholder);
            }
            mMainLayout.addView(mEditText, createEditTextLayoutParams());
            mMainLayout.addView(mRightImageView, createRightIconLayoutParams());

            parent.addView(mMainLayout);
        }

        protected RelativeLayout.LayoutParams createEditTextLayoutParams() {
            RelativeLayout.LayoutParams editLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editLp.addRule(RelativeLayout.LEFT_OF, mRightImageView.getId());
            editLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            return editLp;
        }

        protected RelativeLayout.LayoutParams createRightIconLayoutParams() {
            RelativeLayout.LayoutParams rightIconLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightIconLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            rightIconLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            rightIconLp.leftMargin = Tools.dip2px(mContext,5);
            return rightIconLp;
        }

        @Override
        protected void onAfter(VUIDialog dialog, LinearLayout parent) {
            super.onAfter(dialog, parent);
            dialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                }
            });
            mEditText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEditText.requestFocus();
                    ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(mEditText, 0);
                }
            }, 300);
        }

        public EditText getEditText() {
            return mEditText;
        }

        public ImageView getRightImageView() {
            return mRightImageView;
        }
    }
}
