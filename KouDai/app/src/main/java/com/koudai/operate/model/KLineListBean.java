package com.koudai.operate.model;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.github.mikephil.charting.data.CandleEntry;
import com.koudai.operate.utils.KLineDataUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class KLineListBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int ret;
    private Response response;
    public boolean isFormat = false;

    public class Response {
        private String message;
        private Data data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public class Data {

        private List<KLineItemBean> list;
        private TypeData data;

        public TypeData getData() {
            return data;
        }

        public void setData(TypeData data) {
            this.data = data;
        }

        public List<KLineItemBean> getList() {
            return list;
        }

        public void setList(List<KLineItemBean> list) {
            this.list = list;
        }

        public List<String> getTimeStrList() {
            List<String> timeList = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                timeList.add(list.get(i).getTime());
            }
            return timeList;
        }

        public SparseArray<String> getTimeList() {
            SparseArray<String> timeList = new SparseArray<String>();

            if (list.size() < 10) {
                return timeList;
            }
            timeList.put(0, list.get(0).getTime());
            int len = list.size();
            int space = len / 5;
            int count = space;
            int lastIndex = 0;
            String stepTime = list.get(space).getTime();
            if (stepTime.endsWith("1") || stepTime.endsWith("2") || stepTime.endsWith("3")
                    || stepTime.endsWith("4")) {
                for (int i = 1; i < 5; i++) {
                    for (int j = space * i; j > 5; j--) {
                        if (list.get(j).getTime().endsWith("0")) {
                            timeList.put(j, list.get(j).getTime());
                            lastIndex = j;
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                for (int i = 1; i < 5; i++) {
                    for (int j = space * i; j < list.size(); j++) {
                        if (list.get(j).getTime().endsWith("0")) {
                            timeList.put(j, list.get(j).getTime());
                            lastIndex = j;
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            }
            if (lastIndex != 0 && (list.size() - lastIndex > space / 2)) {
                timeList.put(list.size() - 1, list.get(list.size() - 1).getTime());
            }

//            timeList.put(0, "09:30");
//            timeList.put(60, "10:30");
//            timeList.put(121, "11:30/13:00");
//            timeList.put(182, "14:00");
//            timeList.put(241, "15:00");
            return timeList;
        }


        public float getLast30LowMinPrice() {
            float min = 0f;
            try {
                int position = 0;
                if (list.size() > 30) {
                    position = list.size() - 30;
                }
                for (; position < list.size(); position++) {
                    if (Float.parseFloat(list.get(position).getLow()) > 0.01f) {

                        if (min < 0.01f) {
                            min = Float.parseFloat(list.get(position).getLow());
                        }
                        if (min > Float.parseFloat(list.get(position).getLow())) {
                            min = Float.parseFloat(list.get(position).getLow());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return min;
        }

        public float getLast30HighMaxPrice() {
            float max = 0f;
            try {
                int position = 0;
                if (list.size() > 30) {
                    position = list.size() - 30;
                }
                max = Float.parseFloat(list.get(position++).getHigh());
                for (; position < list.size(); position++) {
                    if (max < Float.parseFloat(list.get(position).getHigh())) {
                        max = Float.parseFloat(list.get(position).getHigh());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return max;
        }

        public float getMinPrice() {
            float min = 0f;
            try {
                for (int i = 0; i < list.size(); i++) {
                    if (Float.parseFloat(list.get(i).getNow()) > 0.01f) {

                        if (min < 0.01f) {
                            min = Float.parseFloat(list.get(i).getNow());
                        }
                        if (min > Float.parseFloat(list.get(i).getNow())) {
                            min = Float.parseFloat(list.get(i).getNow());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return min;
        }

        public float getMaxPrice() {
            float max = 0f;
            try {
                max = Float.parseFloat(list.get(0).getNow());
                for (int i = 1; i < list.size(); i++) {
                    if (max < Float.parseFloat(list.get(i).getNow())) {
                        max = Float.parseFloat(list.get(i).getNow());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return max;
        }

        public class TypeData {
            private int goods_id;
            private int type;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<CandleEntry> getCandleEntries(Context context, List<KLineItemBean> kLineList) {
        return getCandleEntries(kLineList, 0);
    }

    public List<CandleEntry> getCandleEntries(List<KLineItemBean> rawData, int startIndex) {
        List<CandleEntry> entries = new ArrayList<>();
        CandleEntry entry;

//        Comparator comparator = new Comparator<StockListBean.StockBean>() {
//            @Override
//            public int compare(StockListBean.StockBean lhs, StockListBean.StockBean rhs) {
//                Date dateL = stringToDate(lhs.getDate(), "yyyy/MM/dd");
//                Date dateR = stringToDate(rhs.getDate(), "yyyy/MM/dd");
//                if (dateL.after(dateR)) {
//                    return 1;
//                }
//                return -1;
//            }
//        };
//        Collections.sort(rawData, comparator);

        for (int i = 0; i < rawData.size(); i++) {
//                public CandleEntry(int xIndex, float shadowH, float shadowL, float open, float close) {
            KLineItemBean stock = rawData.get(i);
            if (stock == null) {
                Log.e("xxx", "第" + i + "StockBean==null");
                continue;
            }
            float hight = Float.parseFloat(stock.getHigh());
            float low = Float.parseFloat(stock.getLow());
//            if (hight>=low) {
            entry = new CandleEntry(startIndex + i, Float.parseFloat(stock.getHigh()), Float.parseFloat(stock.getLow()),
                    Float.parseFloat(stock.getOpen()), Float.parseFloat(stock.getClose()));
//            } else {
//                entry = new CandleEntry(startIndex+i, Float.parseFloat(stock.getHigh()), Float.parseFloat(stock.getLow()),
//                        Float.parseFloat(stock.getOpen()), Float.parseFloat(stock.getClose()));
//            }
//            CandleEntry entry = new CandleEntry(startIndex+i, Float.parseFloat(stock.getLow()), Float.parseFloat(stock.getHigh()),
//                    Float.parseFloat(stock.getOpen()), Float.parseFloat(stock.getClose()));
//            CandleEntry entry = new CandleEntry(startIndex+i, Float.parseFloat(stock.getHigh()), Float.parseFloat(stock.getLow()),
//                    Float.parseFloat(stock.getOpen()), Float.parseFloat(stock.getClose()));
            entries.add(entry);
        }
        getResponse().getData().getLast30HighMaxPrice();
        getResponse().getData().getLast30LowMinPrice();
        KLineDataUtil.getInstance().setData((int)getResponse().getData().getLast30LowMinPrice(), (int)getResponse().getData().getLast30HighMaxPrice());
        return entries;
    }

}
