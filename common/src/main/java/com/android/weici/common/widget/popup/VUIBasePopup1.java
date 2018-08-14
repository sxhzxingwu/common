package com.android.weici.common.widget.popup;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * Created by Mouse on 2017/10/13.
 */

public abstract class VUIBasePopup1 {

    protected Context mContext;
    protected PopupWindow mWindow;
    protected WindowManager mWindowManager;
    protected PopupWindow.OnDismissListener onDismissListener;
    protected RootView mRootViewWrapper;
    protected View mRootView;
    protected Drawable mBackground = null;

    protected Point mScreenSize = new Point();

    protected int mWindowHeight = 0;
    protected int mWindowWidth = 0;
    //cache
    private boolean mNeedCacheSize = true;

    public VUIBasePopup1(Context context){
        this.mContext =context;
        mWindow = new PopupWindow(context);
        mWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_OUTSIDE){
                    return false;
                }
                return false;
            }
        });
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    protected void onDismiss(){}
    protected void onPreShow(){}

    public final void show(View view){
        show(view,view);

    }

    /**
     * On pre show
     */
    private void preShow() {
        if (mRootViewWrapper == null)
            throw new IllegalStateException("setContentView was not called with a view to display.");

        onPreShow();
        if (mBackground == null){
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            mWindow.setBackgroundDrawable(mBackground);
        }

        mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOutsideTouchable(true);

        mWindow.setContentView(mRootViewWrapper);
    }

    protected abstract Point onShow(View attachedView);

    public final void show(View parent,View anchorView){
        preShow();
        Display screenDisplay = mWindowManager.getDefaultDisplay();
        screenDisplay.getSize(mScreenSize);
        if (mWindowWidth == 0 || mWindowHeight == 0 || !mNeedCacheSize) {
            measureWindowSize();
        }

        Point point = onShow(anchorView);
        mWindow.showAtLocation(parent, Gravity.NO_GRAVITY, point.x, point.y);

        // 在相关的View被移除时，window也自动移除。避免当Fragment退出后，Fragment中弹出的PopupWindow还存在于界面上。
        anchorView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });

    }

    private void measureWindowSize() {
        mRootView.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mWindowWidth = mRootView.getMeasuredWidth();
        mWindowHeight = mRootView.getMeasuredHeight();
    }

    public boolean isShowing() {
        return mWindow != null && mWindow.isShowing();
    }

    /**
     * Set content view.
     *
     * @param layoutResID Resource id
     */
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(inflater.inflate(layoutResID, null));
    }

    /**
     * Set content view.
     *
     * @param root Root view
     */
    public void setContentView(View root) {
        if (root == null)
            throw new IllegalStateException("setContentView was not called with a view to display.");
        mRootViewWrapper = new RootView(mContext);
        mRootViewWrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRootView = root;
        mRootViewWrapper.addView(root);
        mWindow.setContentView(mRootViewWrapper);
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                VUIBasePopup1.this.onDismiss();
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener){
        this.onDismissListener = listener;
    }

    public void dismiss(){
        mWindow.dismiss();
    }

    protected void onConfigurationChanged(Configuration newConfig) {

    }

    public class RootView extends FrameLayout{

        public RootView(Context context){
            super(context);
        }

        public RootView(Context context, AttributeSet attrs){
            super(context,attrs);
        }

        @Override
        protected void onConfigurationChanged(Configuration newConfig) {

            if(mWindow !=null&&mWindow.isShowing()){
                mWindow.dismiss();
            }
            VUIBasePopup1.this.onConfigurationChanged(newConfig);
        }
    }


}
