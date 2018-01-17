package com.koudai.operate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.model.TicketInfoBean;

import java.util.List;

public class UserTicketListAdapter extends CommonAdapter<TicketInfoBean> {

    public UserTicketListAdapter(Context context, List<TicketInfoBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket, parent, false);
            holder.findViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(mDatas.get(position));
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView tv_sum;
        private TextView tv_date;
        private TextView tv_status;

        private final int CAN_USE = 1;
        private final int HAS_BENN_USED = 2;
        private final int EXPIRED = 3;

        private void findViews(View rootView) {
            iv_image = (ImageView) rootView.findViewById(R.id.iv_image);
            tv_sum = (TextView) rootView.findViewById(R.id.tv_sum);
            tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            tv_status = (TextView) rootView.findViewById(R.id.tv_status);
        }

        private void setData(TicketInfoBean bean) {
            switch (bean.getType()) {
                case CAN_USE:
                    iv_image.setImageResource(R.mipmap.ticket_enable);
                    tv_status.setText("");
                    break;
                case HAS_BENN_USED:
                    iv_image.setImageResource(R.mipmap.ticket_not_enable);
                    tv_status.setText("已使用");
                    break;
                case EXPIRED:
                    iv_image.setImageResource(R.mipmap.ticket_not_enable);
                    tv_status.setText("已过期");
                    break;
            }
            tv_sum.setText("￥"+bean.getSum());
            tv_date.setText("有效期:"+bean.getAdd_date()+"至"+bean.getEnd_date());
        }
    }
}
