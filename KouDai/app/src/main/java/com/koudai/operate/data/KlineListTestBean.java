package com.koudai.operate.data;

import java.util.List;

/**
 * Created by admin on 2018/1/18.
 */

public class KlineListTestBean {


    /**
     * ret : 0
     * response : {"message":"success","data":{"list":[{"open":"4030","high":"4055","low":"4001","close":"4035","time":"01:01"},{"open":"4050","high":"4055","low":"4021","close":"4045","time":"00:02"},{"open":"4050","high":"4055","low":"4031","close":"4045","time":"01:03"},{"open":"4050","high":"4055","low":"4041","close":"4045","time":"00:04"},{"open":"4050","high":"4055","low":"4011","close":"4045","time":"00:05"}],"data":{"goods_id":20,"type":0}}}
     * benchmark : 0
     */

    private int ret;
    private ResponseBean response;
    private int benchmark;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public int getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(int benchmark) {
        this.benchmark = benchmark;
    }

    public static class ResponseBean {
        /**
         * message : success
         * data : {"list":[{"open":"4030","high":"4055","low":"4001","close":"4035","time":"01:01"},{"open":"4050","high":"4055","low":"4021","close":"4045","time":"00:02"},{"open":"4050","high":"4055","low":"4031","close":"4045","time":"01:03"},{"open":"4050","high":"4055","low":"4041","close":"4045","time":"00:04"},{"open":"4050","high":"4055","low":"4011","close":"4045","time":"00:05"}],"data":{"goods_id":20,"type":0}}
         */

        private String message;
        private DataBeanX data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataBeanX getData() {
            return data;
        }

        public void setData(DataBeanX data) {
            this.data = data;
        }

        public static class DataBeanX {
            /**
             * list : [{"open":"4030","high":"4055","low":"4001","close":"4035","time":"01:01"},{"open":"4050","high":"4055","low":"4021","close":"4045","time":"00:02"},{"open":"4050","high":"4055","low":"4031","close":"4045","time":"01:03"},{"open":"4050","high":"4055","low":"4041","close":"4045","time":"00:04"},{"open":"4050","high":"4055","low":"4011","close":"4045","time":"00:05"}]
             * data : {"goods_id":20,"type":0}
             */

            private DataBean data;
            private List<ListBean> list;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class DataBean {
                /**
                 * goods_id : 20
                 * type : 0
                 */

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

            public static class ListBean {
                /**
                 * open : 4030
                 * high : 4055
                 * low : 4001
                 * close : 4035
                 * time : 01:01
                 */

                private String open;
                private String high;
                private String low;
                private String close;
                private String time;

                public String getOpen() {
                    return open;
                }

                public void setOpen(String open) {
                    this.open = open;
                }

                public String getHigh() {
                    return high;
                }

                public void setHigh(String high) {
                    this.high = high;
                }

                public String getLow() {
                    return low;
                }

                public void setLow(String low) {
                    this.low = low;
                }

                public String getClose() {
                    return close;
                }

                public void setClose(String close) {
                    this.close = close;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }
    }
}
