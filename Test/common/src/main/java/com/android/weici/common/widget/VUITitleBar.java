package com.android.weici.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.weici.common.R;
import com.android.weici.common.Tools;
import com.android.weici.common.widget.help.VUIDrawableHelper;
import com.android.weici.common.widget.help.VUIResHelper;
import com.android.weici.common.widget.help.VUIViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mouse on 2017/9/28.
 */

public class VUITitleBar extends RelativeLayout {


    private static final int DEFAULT_VIEW_ID = -1;
    private int mLeftLastViewId = DEFAULT_VIEW_ID; // 左侧最右 view 的 id
    private int mRightLastViewId = DEFAULT_VIEW_ID; // 右侧最左 view 的 id

    private int mTitleBarSeparatorColor;
    private int mTitleBarSeparatorHeight;
    private int mTitleBarBgColor;
    private int mLeftBackDrawableRes;


    private LinearLayout mTitleContainerView;
    private TextView mTitleView;
    private TextView mSubTitlebView;

    private Drawable mTitleBarBgWithSeparatorDrawable;
    private Rect insets;

    private int mTitlebarImageBtnWidth = -1;
    private int mTitlebarImageBtnHeight = -1;
    private int mTitlebarHeight = -1;
    private int mSubTitlebTextColor = -1;

    private List<View> mLeftViewList;
    private List<View> mRightViewList;

    private int mTitleGravity = Gravity.CENTER;

    public VUITitleBar(Context context) {
        this(context, null);
    }

    public VUITitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.VUITitleBarStyle);
    }

    public VUITitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mLeftViewList = new ArrayList<>();
        mRightViewList = new ArrayList<>();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VUITitleBar, defStyleAttr, 0);
        mTitleBarSeparatorColor = array.getColor(R.styleable.VUITitleBar_vui_titlebar_separator_color, ContextCompat.getColor(context, R.color.vui_config_titlebar_seperator_color));
        mTitleBarSeparatorHeight = array.getDimensionPixelSize(R.styleable.VUITitleBar_vui_titlebar_separator_height, 1);
        mTitleBarBgColor = array.getColor(R.styleable.VUITitleBar_vui_titlebar_bg_color, Color.WHITE);
        mLeftBackDrawableRes = array.getResourceId(R.styleable.VUITitleBar_vui_titlebar_left_back_drawable_id, R.id.vui_titlebar_left_back);
        CharSequence text = array.getText(R.styleable.VUITitleBar_vui_titlebar_title);
        if(!TextUtils.isEmpty(text))
            setTitle(text);
        int left_back = array.getResourceId(R.styleable.VUITitleBar_vui_titlebar_left_back, -1);
        if(left_back != -1) setDefaultBackBtn(left_back);
        int right_img = array.getResourceId(R.styleable.VUITitleBar_vui_titlebar_right_btn_img, -1);
        if(right_img != -1) addRightImageButton(right_img, R.id.vui_titlebar_right_text);
        boolean isNeedShowSeparator = array.getBoolean(R.styleable.VUITitleBar_vui_titlebar_need_serprator, false);
        setBackgroundDividerEnabled(isNeedShowSeparator);
        array.recycle();
    }

    private void setDefaultBackBtn(int left_back){
        addLeftImageButton(left_back, R.id.vui_titlebar_left_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).onBackPressed();
            }
        });
    }
    /**
     * 设置是否要 Topbar 底部的分割线
     *
     * @param enabled
     */
    public void setBackgroundDividerEnabled(boolean enabled) {
        if (enabled) {
            if (mTitleBarBgWithSeparatorDrawable == null) {
                mTitleBarBgWithSeparatorDrawable = VUIDrawableHelper.
                        createItemSeparatorBg(mTitleBarSeparatorColor, mTitleBarBgColor, mTitleBarSeparatorHeight, false);
            }
            VUIViewHelper.setBackgroundKeepingPadding(this, mTitleBarBgWithSeparatorDrawable);
        } else {
            VUIViewHelper.setBackgroundColorKeepPadding(this, mTitleBarBgColor);
        }
    }


    public void setTitle(CharSequence title) {
        TextView titleView = getTitleView();
        titleView.setText(title);
    }

    public TextView getTitleView() {
        if (mTitleView == null) {
            generateTextView();
        }
        return mTitleView;
    }


    private LinearLayout makeTitleContainerView() {
        if (mTitleContainerView == null) {
            mTitleContainerView = new LinearLayout(getContext());
            // 垂直，后面要支持水平的话可以加个接口来设置
            mTitleContainerView.setOrientation(LinearLayout.VERTICAL);
            mTitleContainerView.setGravity(Gravity.CENTER);
            int px = Tools.dip2px(getContext(), 8);
            mTitleContainerView.setPadding(px, 0, px, 0);
            addView(mTitleContainerView, generateTitleContainerViewLp());
        }
        return mTitleContainerView;
    }


    private LayoutParams generateTitleContainerViewLp() {
        LayoutParams titleLp = new LayoutParams(LayoutParams.MATCH_PARENT,
                VUIResHelper.getAttrDimen(getContext(), R.attr.vui_titlebar_height));

        // 左右没有按钮时，title 距离 TopBar 左右边缘的距离
        int titleMarginHorizontalWithoutButton = VUIResHelper.getAttrDimen(getContext(),
                R.attr.vui_ttitlebar_title_margin_horizontal_when_no_btn_aside);
        titleLp.leftMargin = titleMarginHorizontalWithoutButton;
        titleLp.rightMargin = titleMarginHorizontalWithoutButton;

        if (mLeftLastViewId != DEFAULT_VIEW_ID) {
            titleLp.addRule(RelativeLayout.RIGHT_OF, mLeftLastViewId);
        }

        if (mRightLastViewId != DEFAULT_VIEW_ID) {
            titleLp.addRule(RelativeLayout.LEFT_OF, mRightLastViewId);
        }
        return titleLp;
    }

    private LinearLayout.LayoutParams generateTitleViewLp() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return lp;
    }

    private void generateTextView() {
        mTitleView = new TextView(getContext());
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setSingleLine(true);
        mTitleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        mTitleView.setTextColor(VUIResHelper.getAttrColor(getContext(), R.attr.vui_titlebar_text_color));
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Tools.dip2px(getContext(), 18));
        makeTitleContainerView().addView(mTitleView, generateTitleViewLp());
    }


    /**
     * 根据 resourceId 生成一个 TopBar 的按钮，并 add 到 TopBar 的左边
     *
     * @param drawableResId 按钮图片的 resourceId
     * @param viewId        该按钮的 id，可在ids.xml中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return
     */
    public VUIAlphaImageButton addLeftImageButton(int drawableResId, int viewId) {
        VUIAlphaImageButton leftButton = generateTitlebarImageButton(drawableResId);
        this.addLeftView(leftButton, viewId, generateTopBarImageButtonLayoutParams());
        return leftButton;
    }

    /**
     * 生成一个 LayoutParams，当把 Button addView 到 TopBar 时，使用这个 LayouyParams
     */
    public LayoutParams generateTopBarImageButtonLayoutParams() {
        LayoutParams lp = new LayoutParams(getTopBarImageBtnWidth(), getTopBarImageBtnHeight());
        lp.topMargin = Math.max(0, (getTopBarHeight() - getTopBarImageBtnHeight()) / 2);
        return lp;
    }

    private int getTopBarHeight() {
        if (mTitlebarHeight == -1) {
            mTitlebarHeight = VUIResHelper.getAttrDimen(getContext(), R.attr.vui_titlebar_height);
        }
        return mTitlebarHeight;
    }

    private VUIAlphaImageButton generateTitlebarImageButton(int drawableResId) {
        VUIAlphaImageButton backButton = new VUIAlphaImageButton(getContext());
        backButton.setBackgroundColor(Color.TRANSPARENT);
        backButton.setImageResource(drawableResId);
        return backButton;
    }

    private int getTopBarImageBtnWidth() {
        if (mTitlebarImageBtnWidth == -1) {
            mTitlebarImageBtnWidth = VUIResHelper.getAttrDimen(getContext(), R.attr.vui_titlebar_image_btn_width);
        }
        return mTitlebarImageBtnWidth;
    }

    private int getTopBarImageBtnHeight() {
        if (mTitlebarImageBtnHeight == -1) {
            mTitlebarImageBtnHeight = VUIResHelper.getAttrDimen(getContext(), R.attr.vui_titlebar_image_btn_height);
        }
        return mTitlebarImageBtnHeight;
    }

    /**
     * 在TopBar的左侧添加View，如果此前已经有View通过该方法添加到TopBar，则新添加进去的View会出现在已有View的右侧
     *
     * @param view         要添加到 TopBar 左边的 View
     * @param viewId       该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @param layoutParams
     */
    public void addLeftView(View view, int viewId, LayoutParams layoutParams) {
        if (mLeftLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            layoutParams.addRule(RelativeLayout.RIGHT_OF, mLeftLastViewId);
        }
        layoutParams.alignWithParent = true; // alignParentIfMissing
        mLeftLastViewId = viewId;
        view.setId(viewId);
        mLeftViewList.add(view);
        addView(view, layoutParams);
        // 消除按钮变动对 titleView 造成的影响
        refreshTitleViewLp();
    }

    /**
     * 若在 titleView 存在的情况下，改变 leftViews 和 rightViews，会导致 titleView 的位置不正确。
     * 此时要调用该方法，保证 titleView 的位置重新调整
     */
    private void refreshTitleViewLp() {
        // 若原本已经有 title，则需要将title移到新添加进去的按钮右边
        if (mTitleView != null) {
            LayoutParams titleLp = generateTitleContainerViewLp();
            makeTitleContainerView().setLayoutParams(titleLp);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mTitleContainerView != null) {
            // 计算左侧 View 的总宽度
            int leftViewWidth = 0;
            for (int leftViewIndex = 0; leftViewIndex < mLeftViewList.size(); leftViewIndex++) {
                View view = mLeftViewList.get(leftViewIndex);
                if (view.getVisibility() != GONE) {
                    leftViewWidth += view.getMeasuredWidth();
                }
            }
            // 计算右侧 View 的总宽度
            int rightViewWidth = 0;
            for (int rightViewIndex = 0; rightViewIndex < mRightViewList.size(); rightViewIndex++) {
                View view = mRightViewList.get(rightViewIndex);
                if (view.getVisibility() != GONE) {
                    rightViewWidth += view.getMeasuredWidth();
                }
            }
            // 计算 titleContainer 的最大宽度
            int titleContainerWidth = 0;
            if ((mTitleGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                // 标题水平居中，左右两侧的占位要保持一致
                titleContainerWidth = MeasureSpec.getSize(widthMeasureSpec) - Math.max(leftViewWidth, rightViewWidth) * 2 - getPaddingLeft() - getPaddingRight();
            } else {
                // 标题非水平居中，左右两侧的占位按实际计算即可
                titleContainerWidth = MeasureSpec.getSize(widthMeasureSpec) - leftViewWidth - rightViewWidth - getPaddingLeft() - getPaddingRight();
            }
            int titleContainerWidthMeasureSpec = MeasureSpec.makeMeasureSpec(titleContainerWidth, MeasureSpec.EXACTLY);
            mTitleContainerView.measure(titleContainerWidthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mTitleContainerView != null) {
            int titleContainerViewWidth = mTitleContainerView.getMeasuredWidth();
            int titleContainerViewHeight = mTitleContainerView.getMeasuredHeight();
            int titleContainerViewTop = (b - t - mTitleContainerView.getMeasuredHeight()) / 2;
            int titleContainerViewLeft = getPaddingLeft();
            if ((mTitleGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                // 标题水平居中
                titleContainerViewLeft = (r - l - mTitleContainerView.getMeasuredWidth()) / 2;
            } else {
                // 标题非水平居中
                // 计算左侧 View 的总宽度
                for (int leftViewIndex = 0; leftViewIndex < mLeftViewList.size(); leftViewIndex++) {
                    View view = mLeftViewList.get(leftViewIndex);
                    if (view.getVisibility() != GONE) {
                        titleContainerViewLeft += view.getMeasuredWidth();
                    }
                }
            }
            mTitleContainerView.layout(titleContainerViewLeft, titleContainerViewTop, titleContainerViewLeft + titleContainerViewWidth, titleContainerViewTop + titleContainerViewHeight);
        }
    }

    /**
     * 根据 resourceId 生成一个 TopBar 的按钮，并 add 到 TopBar 的右侧
     *
     * @param drawableResId 按钮图片的 resourceId
     * @param viewId        该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return
     */
    public VUIAlphaImageButton addRightImageButton(int drawableResId, int viewId) {
        VUIAlphaImageButton rightButton = generateTitlebarImageButton(drawableResId);
        this.addRightView(rightButton, viewId, generateTopBarImageButtonLayoutParams());
        return rightButton;
    }
    /**
     * 在 TopBar 的右侧添加 View，如果此前已经有 View 通过该方法添加到 TopBar，则新添加进去的 View 会出现在已有View的左侧
     *
     * @param view         要添加到 TopBar 右边的 View
     * @param viewId       该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @param layoutParams
     */
    public void addRightView(View view, int viewId, LayoutParams layoutParams) {
        if (mRightLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.LEFT_OF, mRightLastViewId);
        }
        layoutParams.alignWithParent = true; // alignParentIfMissing
        mRightLastViewId = viewId;
        view.setId(viewId);
        mRightViewList.add(view);
        addView(view, layoutParams);

        // 消除按钮变动对 titleView 造成的影响
        refreshTitleViewLp();
    }

    /**
     * 添加 TopBar 的副标题
     *
     * @param subTitle TopBar 的副标题
     */
    public void setSubTitle(String subTitle) {
        TextView titleView = getSubTitleView();
        titleView.setText(subTitle);
        if (TextUtils.isEmpty(subTitle)) {
            titleView.setVisibility(GONE);
        } else {
            titleView.setVisibility(VISIBLE);
        }
        // 更新 titleView 的样式（因为有没有 subTitle 会影响 titleView 的样式）
        updateTitleViewStyle();
    }

    private TextView getSubTitleView() {
        if (mSubTitlebView == null) {
            mSubTitlebView = new TextView(getContext());
            mSubTitlebView.setGravity(Gravity.CENTER);
            mSubTitlebView.setSingleLine(true);
            mSubTitlebView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            mSubTitlebView.setTextSize(TypedValue.COMPLEX_UNIT_PX, VUIResHelper.getAttrDimen(getContext(), R.attr.vui_titlebar_subtitle_text_size));
            if(mSubTitlebTextColor==-1){
                mSubTitlebView.setTextColor(VUIResHelper.getAttrColor(getContext(), R.attr.vui_titlebar_subtitle_color));
            }else{
                mSubTitlebView.setTextColor(mSubTitlebTextColor);
            }
            LinearLayout.LayoutParams titleLp = generateTitleViewAndSubTitleViewLp();
            titleLp.topMargin = Tools.dip2px(getContext(), 1);
            makeTitleContainerView().addView(mSubTitlebView, titleLp);
        }

        return mSubTitlebView;
    }


    public void setmSubTitleTextColor(int color){
        this.mSubTitlebTextColor = color;
        if(null!=mSubTitlebView){
            mSubTitlebView.setTextColor(color);
        }
    }

    /**
     * 生成 titleView 或 subTitleView 的 LayoutParams
     *
     * @return
     */
    private LinearLayout.LayoutParams generateTitleViewAndSubTitleViewLp() {
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 垂直居中
        titleLp.gravity = mTitleGravity;
        return titleLp;
    }

    /**
     * 更新 titleView 的样式（因为有没有 subTitle 会影响 titleView 的样式）
     */
    private void updateTitleViewStyle() {
        if (mTitleView != null) {
            if (mSubTitlebView == null || TextUtils.isEmpty(mSubTitlebView.getText())) {
                mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, VUIResHelper.getAttrDimen(getContext(), R.attr.vui_titlebar_text_size));
            } else {
                mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, VUIResHelper.getAttrDimen(getContext(), R.attr.vui_titlebar_title_text_size_with_subtitle));
            }
        }
    }

    /**
     * 在 TopBar 右边添加一个 Button，并设置文字
     *
     * @param buttonText 按钮的文字
     * @param viewId     该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return
     */
    public Button addRightTextButton(String buttonText, int viewId) {
        Button button = generateTopBarTextButton(buttonText);
        this.addRightView(button, viewId, generateTopBarTextButtonLayoutParams());
        return button;
    }



    /**
     * 生成一个文本按钮，并设置文字
     *
     * @param text 按钮的文字
     * @return
     */
    private Button generateTopBarTextButton(String text) {
        Button button = new Button(getContext());
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setMinWidth(0);
        button.setMinHeight(0);
        button.setMinimumWidth(0);
        button.setMinimumHeight(0);
        int paddingHorizontal = getTopBarTextBtnPaddingHorizontal();
        button.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        button.setTextColor(VUIResHelper.getAttrColorStateList(getContext(), R.attr.vui_titlebar_text_btn_color_state_list));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, Tools.dip2px(getContext(),13));
        button.setGravity(Gravity.CENTER);
        button.setText(text);
        return button;
    }

    private int getTopBarTextBtnPaddingHorizontal() {
        return Tools.dip2px(getContext(),12);
    }

    /**
     * 生成一个LayoutParams，当把 Button addView 到 TopBar 时，使用这个 LayouyParams
     */
    public LayoutParams generateTopBarTextButtonLayoutParams() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, getTopBarImageBtnHeight());
        lp.topMargin = Math.max(0, (getTopBarHeight() - getTopBarImageBtnHeight()) / 2);
        return lp;
    }
}
