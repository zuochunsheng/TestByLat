package com.android.superplayer.ui.widgit;

import android.view.View;
import com.aliyun.svideo.common.base.BaseDialogFragment;
import com.android.superplayer.R;

/**
 * 分享 DialogFragment
 */
public class AlivcShareDialogFragment extends BaseDialogFragment {


    public static AlivcShareDialogFragment newInstance() {
        AlivcShareDialogFragment dialogFragment = new AlivcShareDialogFragment();
        return dialogFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.alivc_long_video_dialogfragment_share;
    }

    @Override
    protected void bindView(View view) {
        view.findViewById(R.id.alivc_tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected int getDialogAnimationRes() {
        return R.style.Dialog_Animation;
    }
}
