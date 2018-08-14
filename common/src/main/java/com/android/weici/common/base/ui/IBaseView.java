package com.android.weici.common.base.ui;

import com.android.weici.common.widget.diaolog.LoadingDialog;

/**
 * Created by Mouse on 2017/11/10.
 */

public interface IBaseView {

    void showMessage(String text);
    void showLoadingState();
    void showFailureState(String text, int code);
    void showFailureState(String text);
    void showSuccessState();
    void showLoadingDialog();
    void dismissLoadingDialog();
    void dismissLoadingDialog(boolean success, String text);
    //加载对话框结束时的一些操作
    void dismissLoadingDialog(boolean success, String text, LoadingDialog.OnDialogDismissListener listener);
}
