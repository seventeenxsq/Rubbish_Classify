package com.example.two_dimentioncodedemo.Bean;

import java.util.List;

public class ImageBean {

    /**
     * code : 200
     * msg : success
     * newslist : [{"keyword":"打火机","trust":100,"lajitype":3,"lajitip":"打火机是其它干垃圾，常见包括砖瓦陶瓷、卫生间废纸、猫砂、毛发、一次性制品等。投放时应尽量沥干水分、平整轻放。"},{"keyword":"圆珠笔","trust":61,"lajitype":3,"lajitip":"圆珠笔是其它干垃圾，常见包括砖瓦陶瓷、卫生间废纸、猫砂、毛发、一次性制品等。投放时应尽量沥干水分、平整轻放。"},{"keyword":"文具盒","trust":40,"lajitype":0,"lajitip":"文具盒是可回收垃圾，常见包括各类废金属、玻璃瓶、饮料瓶、电子产品等。投放时应注意轻投轻放、清洁干燥、避免污染。"},{"keyword":"化妆品","trust":20,"lajitype":1,"lajitip":"化妆品是有毒有害垃圾，常见包括废电池、废油漆桶、各类过期药品等。投放时应注意尽量排空内容物或包裹妥善后投放。"},{"keyword":"护肤品","trust":0,"lajitype":3,"lajitip":"护肤品是其它干垃圾，常见包括砖瓦陶瓷、卫生间废纸、猫砂、毛发、一次性制品等。投放时应尽量沥干水分、平整轻放。"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * keyword : 打火机
         * trust : 100  /置信度
         * lajitype : 3   //干垃圾
         * lajitip : 打火机是其它干垃圾，常见包括砖瓦陶瓷、卫生间废纸、猫砂、毛发、一次性制品等。投放时应尽量沥干水分、平整轻放。
         */

        private String keyword;
        private int trust;
        private int lajitype;
        private String lajitip;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getTrust() {
            return trust;
        }

        public void setTrust(int trust) {
            this.trust = trust;
        }

        public int getLajitype() {
            return lajitype;
        }

        public void setLajitype(int lajitype) {
            this.lajitype = lajitype;
        }

        public String getLajitip() {
            return lajitip;
        }

        public void setLajitip(String lajitip) {
            this.lajitip = lajitip;
        }
    }
}
