package com.hwy.ui.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hwy.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载更多控件
 * Created by heweiyun on 2015/12/29.
 */
public class LoadMoreView extends FrameLayout {

    @BindView(R.id.load_more_tv)
    TextView mTextView;

    @BindView(R.id.load_more_pb)
    ProgressBar mProgressBar;

    private ReloadListener mReloadListener;

    /**加载中*/
    public static final int MODE_LOADING = 1;
    /**加载失败*/
    public static final int MODE_LOAD_FAILED = 2;
    /**没有更多*/
    public static final int MODE_LOAD_NOMORE = 3;
    @IntDef({MODE_LOADING,MODE_LOAD_FAILED,MODE_LOAD_NOMORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode{}

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_load_more, this,true);
        ButterKnife.bind(this);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mReloadListener){
                    mReloadListener.reload();
                }
            }
        });
    }

    public void setReloadListener(ReloadListener reloadListener){
        this.mReloadListener = reloadListener;
    }


    /**
     * 设置为加载中模式
     */
    public void setLoadingMode(){
        mProgressBar.setVisibility(VISIBLE);
        mTextView.setText(R.string.load_more_loading);
    }

    /**
     * 设置为加载失败模式
     */
    public void setLoadFailedMode(){
        mProgressBar.setVisibility(GONE);
        mTextView.setText(R.string.load_more_failed);
    }

    /**
     * 设置为没有更多模式
     */
    public void setLoadNoMoreMode(){
        mProgressBar.setVisibility(GONE);
        mTextView.setText(R.string.load_more_no_more);
    }

    public void setLoadMode(@Mode int mode){
        switch (mode){
            case MODE_LOAD_FAILED:
                setLoadFailedMode();
                break;
            case MODE_LOAD_NOMORE:
                setLoadNoMoreMode();
                break;
            default:
                setLoadingMode();
                break;
        }
    }

}
