package com.koudai.operate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.model.MoneyLogBean;
import com.koudai.operate.model.OrderInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class OrderListAdapter extends CommonAdapter<OrderInfoBean> {
    private OrderListener mListener;

    public OrderListAdapter(Context context, List<OrderInfoBean> datas, OrderListener listener) {
        super(context, datas);
        mListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MyClick myClick = null;
        if (convertView == null) {
            holder = new ViewHolder();
            myClick = new MyClick();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_info, parent, false);
            holder.findViews(convertView);
            holder.tv_more.setOnClickListener(myClick);
            holder.tv_sell.setOnClickListener(myClick);
            convertView.setTag(holder);
            convertView.setTag(R.id.tv_more, myClick);
        } else {
            holder = (ViewHolder) convertView.getTag();
            myClick = (MyClick) convertView.getTag(R.id.tv_more);
        }
        myClick.position = position;
        holder.setData(mDatas.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView tv_buy_type;
        private TextView tv_pro_name;
        private TextView tv_new_price;
        private TextView tv_more;
        private TextView tv_create_price;
        private TextView tv_order_amount;
        private TextView tv_safe_price;
        private TextView tv_profit_loss;
        private TextView tv_sell;


        private void findViews(View rootView) {
            tv_buy_type = (TextView) rootView.findViewById(R.id.tv_buy_type);
            tv_pro_name = (TextView) rootView.findViewById(R.id.tv_pro_name);
            tv_new_price = (TextView) rootView.findViewById(R.id.tv_new_price);
            tv_more = (TextView) rootView.findViewById(R.id.tv_more);
            tv_create_price = (TextView) rootView.findViewById(R.id.tv_create_price);
            tv_order_amount = (TextView) rootView.findViewById(R.id.tv_order_amount);
            tv_safe_price = (TextView) rootView.findViewById(R.id.tv_safe_price);
            tv_profit_loss = (TextView) rootView.findViewById(R.id.tv_profit_loss);
            tv_sell = (TextView) rootView.findViewById(R.id.tv_sell);
        }

        private void setData(OrderInfoBean bean) {
            if (bean != null) {
                if (bean.getTrade_type() == 1) {
                    tv_buy_type.setText("涨");
                    tv_buy_type.setBackgroundResource(R.drawable.trade_type_red_shape);
                } else {
                    tv_buy_type.setText("跌");
                    tv_buy_type.setBackgroundResource(R.drawable.trade_type_green_shape);
                }
                tv_pro_name.setText(bean.getPro_name()+bean.getPro_amount()+bean.getPro_unit());
                if (bean.getRise() >= 0) {
                    tv_new_price.setTextColor(Color.parseColor("#FFF54337"));
                } else {
                    tv_new_price.setTextColor(Color.parseColor("#FF12bc65"));
                }
                tv_new_price.setText(bean.getNew_price() + "");
                tv_create_price.setText(bean.getBuild_price() + "");
                tv_order_amount.setText(bean.getAmount()+"");
                tv_safe_price.setText(bean.getGuaranteed());
                if (bean.getProfitAndLoss() >= 0) {
                    tv_profit_loss.setTextColor(Color.parseColor("#FFF54337"));
                } else {
                    tv_profit_loss.setTextColor(Color.parseColor("#FF12bc65"));
                }
                tv_profit_loss.setText(bean.getProfitAndLoss() + "");
            }
        }
    }

    class MyClick implements View.OnClickListener {
        protected int position;

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.tv_more:
                    mListener.onClickDetail(position);
                    break;
                case R.id.tv_sell:
                    mListener.onClickSell(position);
            }
        }
    }

    public interface OrderListener {
        void onClickDetail(int position);

        void onClickSell(int position);
    }
}
