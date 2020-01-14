package com.example.two_dimentioncodedemo.Bean;

import java.util.List;

public class GarbageBean {
    /**
     * code : 1
     * msg : 查询到6条数据
     * data : [{"id":1004,"name":"小龙虾","type":2,"category":"湿垃圾","remark":"","num":27},{"id":1005,"name":"小龙虾壳","type":2,"category":"湿垃圾","remark":"","num":26},{"id":1006,"name":"小龙虾头","type":2,"category":"湿垃圾","remark":"","num":26},{"id":1007,"name":"小龙虾肉","type":2,"category":"湿垃圾","remark":"","num":26},{"id":1008,"name":"小龙虾香蕉皮","type":2,"category":"湿垃圾","remark":"","num":26},{"id":1009,"name":"小龙虾黄","type":2,"category":"湿垃圾","remark":"","num":26}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1004
         * name : 小龙虾
         * type : 2
         * category : 湿垃圾
         * remark :
         * num : 27
         */

        private int id;
        private String name;
        private int type;
        private String category;
        private String remark;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
