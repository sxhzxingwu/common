package com.android.weici.common.widget.diaolog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.weici.common.R;

public class ClueDialog extends BaseDialog {
	public ClueDialog(Context context) {
		super(context);
	}
	public ClueDialog(Context context, int theme) {
		super(context, theme);
	}

	private void setSize(Context context, double widthScale) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		int windowWidth = outMetrics.widthPixels;
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = (int) (windowWidth * widthScale); // 宽度设置为屏幕的一定比例大小
		getWindow().setAttributes(params);
	}

	public static class Builder{
		private Params mBp;

		public Builder(Context context){
			mBp = new Params();
			mBp.mContext = context;
		}

		public Builder setTitle(CharSequence title){
			mBp.mTitle = title;
			return this;
		}

		public Builder setMessage(CharSequence message){
			mBp.mMessage = message;
			return this;
		}

		public Builder setPositiveButton(CharSequence positiveButtonText, DialogInterface.OnClickListener positiveButtonListener){
			mBp.mPositiveButtonText = positiveButtonText;
			mBp.mPositiveButtonListener = positiveButtonListener;
			return this;
		}

		public Builder setPositiveButtonText(CharSequence positiveButtonText){
			mBp.mPositiveButtonText = positiveButtonText;
			return this;
		}

		public Builder setPositiveButtonListener(DialogInterface.OnClickListener positiveButtonListener){
			mBp.mPositiveButtonListener = positiveButtonListener;
			return this;
		}

		public Builder setNegativeButton(CharSequence negativeButtonText, DialogInterface.OnClickListener negativeButtonListener){
			mBp.mNegativeButtonText = negativeButtonText;
			mBp.mNegativeButtonListener = negativeButtonListener;
			return this;
		}

		public Builder setNegativeButtonText(CharSequence negativeButtonText){
			mBp.mNegativeButtonText = negativeButtonText;
			return this;
		}

		public Builder setNegativeButtonListener(DialogInterface.OnClickListener negativeButtonListener){
			mBp.mNegativeButtonListener = negativeButtonListener;
			return this;
		}

		public Builder setNeutralButton(CharSequence neutralButtonText, DialogInterface.OnClickListener neutralButtonListener){
			mBp.mNeutralButtonText = neutralButtonText;
			mBp.mNeutralButtonListener = neutralButtonListener;
			return this;
		}
		public Builder setCenterButton(CharSequence centerButtonText, DialogInterface.OnClickListener centerButtonListener){
			mBp.mCenterButtonText = centerButtonText;
			mBp.mCenterButtonListener = centerButtonListener;
			return this;
		}
		public Builder setButtonRightTextColor(int color){
			mBp.mBtnRightColorRes = color;
			return this;
		}

		public Builder setMessageGravity(int gravity){
			mBp.gravity = gravity;
			return this;
		}

		public Builder setButtonLeftTextColor(int color){
			mBp.mBtnLeftColorRes = color;
			return this;
		}

		public Builder setNeutralButtonText(CharSequence neutralButtonText){
			mBp.mNeutralButtonText = neutralButtonText;
			return this;
		}

		public Builder setNeutralButtonListener(DialogInterface.OnClickListener neutralButtonListener){
			mBp.mNeutralButtonListener = neutralButtonListener;
			return this;
		}
		public Builder setCenterButtonText(CharSequence centerButtonText){
			mBp.mCenterButtonText = centerButtonText;
			return this;
		}
		public Builder setCenterButtonListener(DialogInterface.OnClickListener centerButtonListener){
			mBp.mCenterButtonListener = centerButtonListener;
			return this;
		}
		private ClueDialog onCreate(){
			final ClueDialog dialog = new ClueDialog(mBp.mContext, R.style.customDialog);
			return dialog;
		}

		public ClueDialog show(){
			ClueDialog dialog = onCreate();
			dialog.setContentView(setView(dialog));
			dialog.setSize(mBp.mContext, 0.8);
			dialog.setCancelable(mBp.cancelAble);
			dialog.show();
			return dialog;
		}

		public void setCancelable(boolean able){
			mBp.cancelAble = able;
		}

        public void setAutoDismiss(boolean auto){
            mBp.autoDismiss = auto;
        }

		private View setView(final Dialog dialog){
			View view = View.inflate(mBp.mContext, R.layout.dialog_clue_layout, null);
			setText(view, R.id.title, mBp.mTitle);
			setText(view, R.id.message, mBp.mMessage);
			setText(view, R.id.message, mBp.gravity);

			View.OnClickListener listener = new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(v.getId() == R.id.negativeButton && mBp.mNegativeButtonListener != null){
						mBp.mNegativeButtonListener.onClick(dialog, 0);
					}else if(v.getId() == R.id.positiveButton && mBp.mPositiveButtonListener != null){
						mBp.mPositiveButtonListener.onClick(dialog, 0);
					}else if(v.getId() == R.id.neutralButton && mBp.mNeutralButtonListener != null){
						mBp.mNeutralButtonListener.onClick(dialog, 0);
					}else if(v.getId() == R.id.centerButton && mBp.mCenterButtonListener != null){
						mBp.mCenterButtonListener.onClick(dialog, 0);
					}
					if(mBp.autoDismiss)
					    dialog.dismiss();
				}
			};

			setText(view, R.id.negativeButton, mBp.mNegativeButtonText).setOnClickListener(listener);
			setText(view, R.id.positiveButton, mBp.mPositiveButtonText).setOnClickListener(listener);
			setText(view, R.id.neutralButton, mBp.mNeutralButtonText).setOnClickListener(listener);
			setText(view, R.id.centerButton, mBp.mCenterButtonText).setOnClickListener(listener);
			setTextColor(view, R.id.positiveButton, mBp.mBtnRightColorRes,mBp.mContext);
			setTextColor(view, R.id.negativeButton, mBp.mBtnLeftColorRes,mBp.mContext);
			return view;
		}

		private void setTextColor(View view, int vId,int color,Context context){
			if(color<=0){
				return;
			}
			TextView v = (TextView)view.findViewById(vId);
			v.setTextColor(context.getResources().getColor(color));
		}

		private View setText(View view, int vId, CharSequence text){
			TextView v = (TextView)view.findViewById(vId);
			if(!TextUtils.isEmpty(text)){
				v.setText(text);
				v.setVisibility(View.VISIBLE);
			}else{
				v.setVisibility(View.GONE);
			}

			return v;
		}

		private View setText(View view, int vId, int gravity){
			TextView v = (TextView)view.findViewById(vId);
			v.setGravity(gravity);
			return v;
		}
	}

	private static class Params{
		public Context mContext;
		CharSequence mTitle;
		CharSequence mMessage;
		CharSequence mPositiveButtonText;
		DialogInterface.OnClickListener mPositiveButtonListener;
		CharSequence mNegativeButtonText;
		DialogInterface.OnClickListener mNegativeButtonListener;
		CharSequence mNeutralButtonText;
		DialogInterface.OnClickListener mNeutralButtonListener;
		CharSequence mCenterButtonText;
		DialogInterface.OnClickListener mCenterButtonListener;
		int mBtnRightColorRes;
		int mBtnLeftColorRes;
		int gravity = Gravity.LEFT|Gravity.CENTER_VERTICAL;

		boolean cancelAble = true;
		boolean autoDismiss = true;
	}
}
