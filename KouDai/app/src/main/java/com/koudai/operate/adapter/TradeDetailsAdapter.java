package com.koudai.operate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.koudai.operate.R;
import com.koudai.operate.model.OrderListItemBean;
import com.koudai.operate.model.TradeDetailItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class TradeDetailsAdapter extends BaseExpandableListAdapter {

    private Context mCoontext;
    private List<OrderListItemBean> mList = new ArrayList<OrderListItemBean>();

    public TradeDetailsAdapter(List<OrderListItemBean> list, Context context) {
        mList = list;
        mCoontext = context;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return mList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mList.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder holder = null;
        if (view == null) {
            holder = new GroupViewHolder();
            view = LayoutInflater.from(mCoontext).inflate(R.layout.item_group_trade_details, viewGroup, false);
            holder.findViews(view);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
        if (mList.get(i) != null) {
            holder.setData(mList.get(i));
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (view == null) {
            holder = new ChildViewHolder();
            view = LayoutInflater.from(mCoontext).inflate(R.layout.item_child_trade_details, viewGroup, false);
            holder.findViews(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        if (mList.get(i) != null) {
            holder.setData(mList.get(i));
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class GroupViewHolder {
        TextView tv_buy_date;
        TextView tv_product;
        TextView tv_weight;
        TextView tv_buy_type;
        TextView tv_result;

        private void findViews(View rootView) {
            tv_buy_date = (TextView) rootView.findViewById(R.id.tv_buy_date);
            tv_product = (TextView) rootView.findViewById(R.id.tv_product);
            tv_weight = (TextView) rootView.findViewById(R.id.tv_weight);
            tv_buy_type = (TextView) rootView.findViewById(R.id.tv_buy_type);
            tv_result = (TextView) rootView.findViewById(R.id.tv_result);
        }

        private void setData(OrderListItemBean bean) {
            tv_buy_date.setText(bean.getBuild_time().substring(5, 10));
            tv_product.setText(bean.getPro_name());
            tv_weight.setText(bean.getAmount() + "手");
            if (bean.getTrade_type() == 1) {
                tv_buy_type.setText("涨");
                tv_buy_type.setBackgroundResource(R.drawable.trade_type_red_shape);
            } else {
                tv_buy_type.setText("跌");
                tv_buy_type.setBackgroundResource(R.drawable.trade_type_green_shape);
            }
            tv_result.setText(bean.getPro_loss());
        }
    }

    class ChildViewHolder {
        TextView tv_buy_unit_price;
        TextView tv_sell_unit_price;
        TextView tv_buy_total_price;
        TextView tv_poundage;
        TextView tv_result;
        TextView tv_real_result;
        TextView tv_money_type;
        TextView tv_buy_time;
        TextView tv_sell_time;
        TextView tv_sell_type;

        private void findViews(View rootView) {
            tv_buy_unit_price = (TextView) rootView.findViewById(R.id.tv_buy_unit_price);
            tv_sell_unit_price = (TextView) rootView.findViewById(R.id.tv_sell_unit_price);
            tv_buy_total_price = (TextView) rootView.findViewById(R.id.tv_buy_total_price);
            tv_poundage = (TextView) rootView.findViewById(R.id.tv_poundage);
            tv_money_type = (TextView) rootView.findViewById(R.id.tv_money_type);
            tv_buy_time = (TextView) rootView.findViewById(R.id.tv_buy_time);
            tv_sell_time = (TextView) rootView.findViewById(R.id.tv_sell_time);
            tv_sell_type = (TextView) rootView.findViewById(R.id.tv_sell_type);
            tv_result = (TextView) rootView.findViewById(R.id.tv_result);
            tv_real_result = (TextView) rootView.findViewById(R.id.tv_real_result);
        }

        private void setData(OrderListItemBean bean) {
            tv_buy_unit_price.setText(bean.getBuild_price() + "");
            tv_sell_unit_price.setText(bean.getLiqui_price() + "");
            tv_buy_total_price.setText(bean.getTrade_deposit());
            tv_poundage.setText(bean.getTrade_fee() + "");
            tv_result.setText(bean.getPro_loss() + "元");
            tv_real_result.setText(bean.getActual_pro_loss() + "元");
            tv_poundage.setText(bean.getTrade_fee() + "");
            tv_money_type.setText(bean.getUse_ticket() == 1 ? "代金券" : "现金");
            tv_buy_time.setText(bean.getBuild_time());
            tv_sell_time.setText(bean.getLiqui_time());
            String type = "";
            switch (bean.getLiqui_type()) {
                case 1:
                    type = "爆仓";
                    break;
                case 2:
                    type = "手动平仓";
                    break;
                case 3:
                    type = "止赢平仓";
                    break;
                case 4:
                    type = "止损平仓";
                    break;
                case 5:
                    type = "结算平仓";
                    break;
            }
            tv_sell_type.setText(type);
        }
    }
}
