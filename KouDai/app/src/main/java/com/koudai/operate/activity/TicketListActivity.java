package com.koudai.operate.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.koudai.operate.R;
import com.koudai.operate.adapter.UserTicketListAdapter;
import com.koudai.operate.base.BaseActivity;
import com.koudai.operate.model.ResUserTicketListBean;
import com.koudai.operate.model.TicketInfoBean;
import com.koudai.operate.net.api.UserAction;
import com.koudai.operate.net.base.BaseNetCallBack;
import com.koudai.operate.net.base.NetBase;
import com.koudai.operate.utils.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketListActivity extends BaseActivity {
    private ListView lv_ticket;
    private List<TicketInfoBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getTicketList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "1");
            new UserAction(this).getUserTicket(jsonObject, new BaseNetCallBack<ResUserTicketListBean>() {
                @Override
                public void onSuccess(ResUserTicketListBean paramT) {
                    if (paramT.getResponse().getData() != null) {
                        list = paramT.getResponse().getData().getList();
                        int ticet10 = 0;
                        int ticket200 = 0;
                        for (TicketInfoBean ticket : list) {
                            if (ticket.getType() == 1) {
                                if (ticket.getSum() == 10 && ticket.getType() == 1) {
                                    ticet10 += 1;
                                } else if (ticket.getSum() == 200 && ticket.getType() == 1) {
                                    ticket200 += 1;
                                }
                            }
                        }
                        UserUtil.setTicekt10(mContext, ticet10);
                        UserUtil.setTicekt200(mContext, ticket200);
                        UserTicketListAdapter adapter = new UserTicketListAdapter(TicketListActivity.this, list);
                        lv_ticket.setAdapter(adapter);
                        setResult(RESULT_OK);
                    }
                }

                @Override
                public void onFailure(String url, NetBase.ErrorType errorType, int errorCode) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list.size() == 0) {
            getTicketList();
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_ticket_list;
    }

    @Override
    protected void findViews() {
        lv_ticket = (ListView) findViewById(R.id.lv_ticket);
    }

    @Override
    protected void setListensers() {

    }
}
