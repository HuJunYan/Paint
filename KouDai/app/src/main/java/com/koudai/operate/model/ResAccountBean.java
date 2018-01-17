package com.koudai.operate.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ResAccountBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private String frozen_balance;
            private String available_balance;
            private String total_balance;
            private String mobile;
            private int use_ticket_count;
            private String nickname;
            private List<Ticket> ticket;

            public class Ticket {
                private String name;
                private int count;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }
            }

            public String getFrozen_balance() {
                return frozen_balance;
            }

            public void setFrozen_balance(String frozen_balance) {
                this.frozen_balance = frozen_balance;
            }

            public String getAvailable_balance() {
                return available_balance;
            }

            public void setAvailable_balance(String available_balance) {
                this.available_balance = available_balance;
            }

            public String getTotal_balance() {
                return total_balance;
            }

            public void setTotal_balance(String total_balance) {
                this.total_balance = total_balance;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public List<Ticket> getTicket() {
                return ticket;
            }

            public void setTicket(List<Ticket> ticket) {
                this.ticket = ticket;
            }

            public int getUse_ticket_count() {
                return use_ticket_count;
            }

            public void setUse_ticket_count(int use_ticket_count) {
                this.use_ticket_count = use_ticket_count;
            }
        }

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
}
