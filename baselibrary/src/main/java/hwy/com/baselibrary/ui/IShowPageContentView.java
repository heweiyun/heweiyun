package hwy.com.baselibrary.ui;

/**
 * 功能描述：分页加载的页面
 * 创建人：heweiyun
 * 创建时间：2016/12/2 16:46
 * 修改人：heweiyun
 * 修改时间：2016/12/2 16:46
 * 修改备注：
 */

public interface IShowPageContentView<D> extends IShowContentView<D>{

    /**
     * 加载分页失败
     */
    void onLoadMoreError();

    /**
     * 加载分页成功
     * @param data
     */
    void onLoadMore(D data);

    /**
     * 没有更多数据
     */
    void onLoadNomore();
}
