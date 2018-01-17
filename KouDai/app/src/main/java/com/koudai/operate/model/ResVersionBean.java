package com.koudai.operate.model;

/**
 * Created by Administrator on 2016/9/19.
 */
public class ResVersionBean {
    private int ret;
    private Response response;

    public class Response {
        private String message;
        private Data data;

        public class Data {
            private String version;
            private String url;
            private int is_update;
            private String content;

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getIs_update() {
                return is_update;
            }

            public void setIs_update(int is_update) {
                this.is_update = is_update;
            }

            public String getContent() {
                return content == null ? "" : content;
            }

            public void setContent(String content) {
                this.content = content;
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
