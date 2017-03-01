package hwy.com.baselibrary.ui;

/**
 * 功能描述：只显示一页加载内容的View
 * 创建人：heweiyun
 * 创建时间：2016/12/2 16:41
 * 修改人：heweiyun
 * 修改时间：2016/12/2 16:41
 * 修改备注：
 */

public interface IShowContentView<D> extends IBaseView{

    /**
     * 加载中
     */
    void onLoading();

    /**
     * 加载失败
     */
    void onLoadError();

    /**
     * 加载成功
     * @param data
     */
    void onLoadSuccess(D data);

    /**
     * 加载内容为空
     */
    void onLoadEmpty();
}
