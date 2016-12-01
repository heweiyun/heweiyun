package com.hwy.ui.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hwy.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/29 14:44
 * 修改人：heweiyun
 * 修改时间：2016/11/29 14:44
 * 修改备注：
 */

public class LoadView extends LinearLayout{

    @BindView(R.id.load_tv)
    TextView mTextView;

    @BindView(R.id.load_pb)
    ProgressBar mProgressBar;

    @BindView(R.id.load_root)
    LinearLayout mLlRoot;

    private ReloadListener mReloadListener;

    private int mCurState;

    /**加载中*/
    public static final int MODE_LOADING = 1;
    /**加载失败*/
    public static final int MODE_LOAD_FAILED = 2;
    /**加载成功*/
    public static final int MODE_LOAD_SUCCESS = 3;
    @IntDef({MODE_LOADING,MODE_LOAD_FAILED,MODE_LOAD_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode{}

    public LoadView(Context context) {
        this(context,null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_load,this,true);
        ButterKnife.bind(this);
        setLoadingMode();
        mLlRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mReloadListener && mCurState == MODE_LOAD_FAILED) {
                    mReloadListener.reload();
                }
            }
        });

    }

    /**
     * 设置重新加载回调
     * @param reloadListener
     */
    public void setReloadListener(ReloadListener reloadListener){
        this.mReloadListener = reloadListener;
    }

    /**
     * 设置为加载成功状态
     */
    public void setLoadSuccessMode(){
        this.setVisibility(View.GONE);
        mCurState = MODE_LOAD_SUCCESS;
    }

    /**
     * 设置为加载失败状态
     */
    public void setLoadFailedMode(){
        this.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(GONE);
        mTextView.setText(R.string.load_more_failed);
        mCurState = MODE_LOAD_FAILED;
    }

    /**
     * 设置为加载中状态
     */
    public void setLoadingMode(){
        this.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mTextView.setText(R.string.load_more_loading);
        mCurState = MODE_LOADING;
    }

    public void setLoadMode(@Mode int mode){
        mCurState = mode;
        switch (mode){
            case MODE_LOAD_FAILED:
                setLoadFailedMode();
                break;
            case MODE_LOAD_SUCCESS:
                setLoadSuccessMode();
                break;
            default:
                setLoadingMode();
                break;
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //屏蔽该控件的触摸事件
        return true;
    }
}
