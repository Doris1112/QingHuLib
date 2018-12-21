package com.qhsd.library.adapter;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public interface BaseRecyclerAdapterCallback<Data> {

    /**
     * 更新Holder
     * @param data 新的data
     * @param holder 需要更新的holder
     */
    void update(Data data, BaseRecyclerAdapter.ViewHolder<Data> holder);
}
