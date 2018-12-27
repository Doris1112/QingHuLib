package com.qhsd.library.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qhsd.library.R;

import java.util.List;

/**
 * @author Doris.
 * @date 2018/12/27.
 */

public class ChooseListPopupWindow extends BasePopupWindow {

    private Context mContext;
    private ListView mChooseList;
    private List<String> mData;
    private OnItemClickListener mListener;

    public ChooseListPopupWindow(Context context, List<String> data){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.lib_popup_choose_list, null);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000333333);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.lib_popup_bottom_anim_style);

        mContext = context;
        mData = data;
        mChooseList = contentView.findViewById(R.id.lib_choose_list);
        contentView.findViewById(R.id.lib_choose_list_cancel)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        initChooseList();
    }

    private void initChooseList(){
        ChooseListAdapter adapter = new ChooseListAdapter();
        mChooseList.setAdapter(adapter);
        mChooseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (mListener != null){
                    mListener.onItemClick(mData.get(position), position);
                }
            }
        });
    }

    private class ChooseListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView itemView;
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.lib_item_choose_list, null);
                itemView = convertView.findViewById(R.id.lib_item_choose_list);
                convertView.setTag(itemView);
            } else {
                itemView = (TextView) convertView.getTag();
            }
            itemView.setText(getItem(position));
            return convertView;
        }
    }

    /**
     * 设置Item点击事件
     * @param listener OnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener{

        /**
         * Item 点击事件
         * @param data String
         * @param position position
         */
        void onItemClick(String data, int position);
    }
}
