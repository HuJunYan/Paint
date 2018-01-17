package com.koudai.operate.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.activity.RechargeHistoryActivity;
import com.koudai.operate.model.MoneyLogBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class RechargeHistoryAdapter extends CommonAdapter<MoneyLogBean> {
    public RechargeHistoryAdapter(Context context, List<MoneyLogBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_recharge_history, parent, false);
            holder.findViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(mDatas.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_type;
        private TextView tv_date;
        private TextView tv_amount;

        private final int MONEY_IN = 3;
        private final int MONEY_OUT = 4;

        private void findViews(View rootView) {
            tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            tv_amount = (TextView) rootView.findViewById(R.id.tv_amount);
        }

        private void setData(MoneyLogBean bean) {
            switch (bean.getType()) {
                case MONEY_IN:
                    tv_type.setText("充值");
                    tv_amount.setText(bean.getIncome() + "元");
                    tv_amount.setTextColor(Color.parseColor("#FFF54337"));
                    break;
                case MONEY_OUT:
                    tv_type.setText("提现");
                    tv_amount.setText(bean.getPay() + "元");
                    tv_amount.setTextColor(Color.parseColor("#FF12bc65"));
                    break;
            }
            tv_date.setText(bean.getAdd_time());
        }
    }
}
