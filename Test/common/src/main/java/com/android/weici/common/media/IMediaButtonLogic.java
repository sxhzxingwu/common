package com.android.weici.common.media;

import android.widget.TextView;

/**
 * Created by weici on 2018/1/23.
 */

public interface IMediaButtonLogic extends IMediaPlayerLogic{
    void setButton(TextView button);
    TextView getButton();
}
