package com.android.weici.common.widget.diaolog;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.weici.common.R;
import com.android.weici.common.widget.help.VUIResHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author cginechen
 * @date 2015-10-20
 */
public class VUIDialogAction {

    @IntDef({ACTION_TYPE_NORMAL, ACTION_TYPE_BLOCK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @IntDef({ACTION_PROP_NEGATIVE, ACTION_PROP_NEUTRAL, ACTION_PROP_POSITIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Prop {
    }

    //类型
    public static final int ACTION_TYPE_NORMAL = 0;
    public static final int ACTION_TYPE_BLOCK = 1;
    //用于标记positive/negative/neutral
    public static final int ACTION_PROP_POSITIVE = 0;
    public static final int ACTION_PROP_NEUTRAL = 1;
    public static final int ACTION_PROP_NEGATIVE = 2;


    private Context mContext;
    private int mIconRes;
    private String mStr;
    private int mActionType;
    private int mActionProp;
    private ActionListener mOnClickListener;
    private Button mButton;

    //region 构造器

    /**
     * 正常类型无图标Action
     *
     * @param context         context
     * @param strRes          文案
     * @param onClickListener 点击事件
     */
    public VUIDialogAction(Context context, int strRes, ActionListener onClickListener) {
        this(context, 0, strRes, ACTION_TYPE_NORMAL, onClickListener);
    }

    public VUIDialogAction(Context context, String str, ActionListener onClickListener) {
        this(context, 0, str, ACTION_TYPE_NORMAL, onClickListener);
    }

    /**
     * 无图标Action
     *
     * @param context         context
     * @param iconRes         图标
     * @param strRes          文案
     * @param onClickListener 点击事件
     */
    public VUIDialogAction(Context context, int iconRes, int strRes, ActionListener onClickListener) {
        this(context, iconRes, strRes, ACTION_TYPE_NORMAL, onClickListener);
    }

    public VUIDialogAction(Context context, int iconRes, String str, ActionListener onClickListener) {
        this(context, iconRes, str, ACTION_TYPE_NORMAL, onClickListener);
    }

    /**
     * 无图标Action
     *
     * @param context         context
     * @param iconRes         图标
     * @param strRes          文案
     * @param actionType      类型
     * @param onClickListener 点击事件
     */
    public VUIDialogAction(Context context, int iconRes, int strRes, @Type int actionType, ActionListener onClickListener) {
        this(context, iconRes, strRes, actionType, ACTION_PROP_NEUTRAL, onClickListener);
    }

    public VUIDialogAction(Context context, int iconRes, String str, @Type int actionType, ActionListener onClickListener) {
        this(context, iconRes, str, actionType, ACTION_PROP_NEUTRAL, onClickListener);
    }


    /**
     * @param context         context
     * @param iconRes         图标
     * @param strRes          文案
     * @param actionType      类型
     * @param actionProp      属性
     * @param onClickListener 点击事件
     */
    public VUIDialogAction(Context context, int iconRes, int strRes, @Type int actionType, @Prop int actionProp, ActionListener onClickListener) {
        mContext = context;
        mIconRes = iconRes;
        mStr = mContext.getResources().getString(strRes);
        mActionType = actionType;
        mActionProp = actionProp;
        mOnClickListener = onClickListener;
    }

    public VUIDialogAction(Context context, int iconRes, String str, @Type int actionType, @Prop int actionProp, ActionListener onClickListener) {
        mContext = context;
        mIconRes = iconRes;
        mStr = str;
        mActionType = actionType;
        mActionProp = actionProp;
        mOnClickListener = onClickListener;
    }
    //endregion


    public void setOnClickListener(ActionListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    //FIXME 这个button是在create之后才生成，存在null指针的问题
    public Button getButton() {
        return mButton;
    }

    public View generateActionView(Context context, final VUIDialog dialog, final int index, boolean hasLeftMargin) {
        mButton = null;
        if (mActionType == ACTION_TYPE_BLOCK) {
            BlockActionView actionView = new BlockActionView(context, mStr, mIconRes);
            mButton = actionView.getButton();
            if (mOnClickListener != null) {
                actionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mButton.isEnabled()) {
                            mOnClickListener.onClick(dialog, index);
                        }
                    }
                });
            }
            return actionView;
        } else {
            mButton = VUIDialogAction.generateSpanActionButton(context, mStr, mIconRes, hasLeftMargin);
            if (mActionProp == ACTION_PROP_NEGATIVE) {
                mButton.setTextColor(VUIResHelper.getAttrColorStateList(mContext, R.attr.vui_dialog_action_text_negative_color));
            } else {
                mButton.setTextColor(VUIResHelper.getAttrColorStateList(mContext, R.attr.vui_dialog_action_text_color));
            }
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mButton.isEnabled()) {
                        mOnClickListener.onClick(dialog, index);
                    }
                }
            });
            return mButton;
        }
    }

    /**
     * 生成适用于对话框的按钮
     */
    public static Button generateSpanActionButton(Context context, String text, int iconRes, boolean hasLeftMargin) {
        Button button = new Button(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_height));
        if (hasLeftMargin) {
            lp.leftMargin = VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_margin_left);
        }
        button.setLayoutParams(lp);
        button.setMinHeight(VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_height));
        button.setMinWidth(VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_min_width));
        button.setMinimumWidth(VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_min_width));
        button.setMinimumHeight(VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_height));
        button.setText(text);
        if (iconRes != 0) {
            Drawable drawable = context.getResources().getDrawable(iconRes);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                button.setCompoundDrawables(drawable, null, null, null);
                button.setCompoundDrawablePadding(VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_drawable_padding));
            }

        }
        button.setGravity(Gravity.CENTER);
        button.setClickable(true);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_text_size));
        button.setTextColor(VUIResHelper.getAttrColorStateList(context, R.attr.vui_dialog_action_text_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(VUIResHelper.getAttrDrawable(context, R.attr.vui_dialog_action_btn_bg));
        }else{
            button.setBackgroundDrawable(VUIResHelper.getAttrDrawable(context, R.attr.vui_dialog_action_btn_bg));
        }
        final int paddingHor = VUIResHelper.getAttrDimen(context, R.attr.vui_dialog_action_button_padding_horizontal);
        button.setPadding(paddingHor, 0, paddingHor, 0);
        return button;
    }

    public static class BlockActionView extends FrameLayout {

        private Button mButton;

        public BlockActionView(Context context, String text, int iconRes) {
            super(context);
            init(text, iconRes);
        }

        private void init(String text, int iconRes) {
            LinearLayout.LayoutParams parentLp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    VUIResHelper.getAttrDimen(getContext(), R.attr.vui_dialog_action_block_btn_height));
            setLayoutParams(parentLp);
            setBackground(VUIResHelper.getAttrDrawable(getContext(), R.attr.vui_dialog_action_block_btn_bg));
            setPadding(
                    VUIResHelper.getAttrDimen(getContext(), R.attr.vui_dialog_padding_horizontal),
                    0,
                    VUIResHelper.getAttrDimen(getContext(), R.attr.vui_dialog_padding_horizontal),
                    0
            );

            mButton = new Button(getContext());
            mButton.setBackgroundResource(0);
            mButton.setPadding(0, 0, 0, 0);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.RIGHT;
            mButton.setLayoutParams(lp);
            mButton.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            mButton.setText(text);
            if (iconRes != 0) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), iconRes);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    mButton.setCompoundDrawables(drawable, null, null, null);
                    mButton.setCompoundDrawablePadding(VUIResHelper.getAttrDimen(getContext(), R.attr.vui_dialog_action_drawable_padding));
                }

            }
            mButton.setMinHeight(0);
            mButton.setMinWidth(0);
            mButton.setMinimumWidth(0);
            mButton.setMinimumHeight(0);
            mButton.setClickable(false);
            mButton.setDuplicateParentStateEnabled(true);
            mButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, VUIResHelper.getAttrDimen(getContext(), R.attr.vui_dialog_action_button_text_size));
            mButton.setTextColor(VUIResHelper.getAttrColorStateList(getContext(), R.attr.vui_dialog_action_text_color));

            addView(mButton);
        }

        public Button getButton() {
            return mButton;
        }

    }

    public int getActionProp() {
        return mActionProp;
    }

    public interface ActionListener {
        void onClick(VUIDialog dialog, int index);
    }

}
