package com.android.weici.common.widget.diaolog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.weici.common.R;
import com.android.weici.common.Tools;
import com.android.weici.common.widget.WButton;
import com.android.weici.common.widget.WLinearLayout;

public class WCUIDialogBuilder {
    protected WCUIDialog mWcuiDialog;
    protected Context mContext;
    protected WLinearLayout mMenuItemContainer;

    protected View createView(){
        mMenuItemContainer = new WLinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mMenuItemContainer.setPadding(
                0, Tools.dip2px(mContext, 10),
                0, Tools.dip2px(mContext, 10)
        );
        mMenuItemContainer.WViewHelper.setRadius(Tools.dip2px(mContext, 5f));
        mMenuItemContainer.WViewHelper.setBackgroundColor(Color.WHITE);
        mMenuItemContainer.setLayoutParams(layoutParams);
        mMenuItemContainer.setOrientation(LinearLayout.VERTICAL);
        return mMenuItemContainer;
    }

    protected TextView createItemTextView(){
        TextView tv = new TextView(mContext);
        tv.setTextColor(Color.BLACK);
        int padding = Tools.dip2px(mContext, 12);
        tv.setPadding(Tools.dip2px(mContext, 20), padding, padding, padding);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);

        tv.setBackgroundResource(R.drawable.ripple_item_selector);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, Tools.dip2px(mContext, 16));
        tv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        tv.setDuplicateParentStateEnabled(false);
        return tv;
    }

    protected WButton createButton(){
        WButton tv = new WButton(mContext);
        tv.setTextColor(Color.BLACK);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Tools.dip2px(mContext, 30));
        tv.setLayoutParams(lp);
        tv.WViewHelper.setRadius(Tools.dip2px(mContext, 5));
        tv.WViewHelper.setRippleColor(0xffe0e0e0);
        tv.WViewHelper.setBackgroundColor(0xffffffff);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, Tools.dip2px(mContext, 14));
        tv.setDuplicateParentStateEnabled(false);
        return tv;
    }

    public static class MenuDialogBuilder extends WCUIDialogBuilder{
        public MenuDialogBuilder(Context context){
            mContext = context;
        }

        public WCUIDialog addItemsView(String[] items, final DialogInterface.OnClickListener listener){
            final WCUIDialog dialog = new WCUIDialog(mContext);
            View view = createView();
            int index=0;
            for (String item : items) {
                TextView itemView = createItemTextView();
                itemView.setText(item);
                mMenuItemContainer.addView(itemView);
                final int finalIndex = index;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(dialog, finalIndex);
                    }
                });
                index ++;
            }
            dialog.setContentView(view);
            dialog.setSize(0.75f);
            dialog.show();
            return dialog;
        }
    }

    public static class MessageDialogBuilder extends WCUIDialogBuilder{
        private TextView mTitle, mMessage;
        private WButton mLeft, mRight;
        private View mDialogContent;
        public MessageDialogBuilder(Context context){
            mContext = context;
            initView();
        }

        private void initView(){
            mDialogContent = createView();
            mTitle = createItemTextView();
            mMessage = createItemTextView();
            mLeft = createButton();
            mRight = createButton();

            mMessage.setTextColor(ContextCompat.getColor(mContext, R.color.vui_config_color_50_pure_black));
            mMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, Tools.dip2px(mContext, 14));

            mMenuItemContainer.setPadding(
                    0, Tools.dip2px(mContext, 10),
                    Tools.dip2px(mContext, 10), Tools.dip2px(mContext, 10)
            );

            mMenuItemContainer.addView(mTitle);
            mMenuItemContainer.addView(mMessage);

            LinearLayout buttonLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity= Gravity.RIGHT|Gravity.CENTER_VERTICAL;
            buttonLayout.setLayoutParams(layoutParams);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

            buttonLayout.addView(mLeft);
            buttonLayout.addView(mRight);
            mMenuItemContainer.addView(buttonLayout);
        }
        public MessageDialogBuilder setTitle(String title){
            mTitle.setText(title);
            return this;
        }

        public MessageDialogBuilder setMessage(String message){
            mMessage.setText(message);
            return this;
        }

        public MessageDialogBuilder setMessage(CharSequence message){
            mMessage.setText(message);
            return this;
        }

        public MessageDialogBuilder setLeftButton(String left, final DialogInterface.OnClickListener onClickListener){
            mLeft.setText(left);
            mLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null)
                    onClickListener.onClick(mWcuiDialog, 0);
                }
            });
            return this;
        }

        public MessageDialogBuilder setRightButton(String right, final DialogInterface.OnClickListener onClickListener){
            mRight.setText(right);
            mRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null)
                        onClickListener.onClick(mWcuiDialog, 0);
                }
            });
            return this;
        }

        public void show(){
            mWcuiDialog = new WCUIDialog(mContext);
            mWcuiDialog.setContentView(mDialogContent);
            mWcuiDialog.setSize(0.75f);
            mWcuiDialog.show();
        }
    }

}
