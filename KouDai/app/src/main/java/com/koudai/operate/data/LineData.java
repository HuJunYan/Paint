package com.koudai.operate.data;

/**
 * Created by admin on 2018/1/17.
 */

public class LineData {

    private static final String line5 = "{\"ret\":0,\"response\":{\"message\":\"success\",\"data\":{\"list\":[{\"open\":\"4030\",\"high\":\"4055\",\"low\":\"4001\",\"close\":\"4035\",\"time\":\"01:01\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4021\",\"close\":\"4045\",\"time\":\"00:02\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4031\",\"close\":\"4045\",\"time\":\"01:03\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4041\",\"close\":\"4045\",\"time\":\"00:04\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4011\",\"close\":\"4045\",\"time\":\"00:05\"}],\"data\":{\"goods_id\":20,\"type\":0},},},\"benchmark\":0}";

    private static final String line15 ="{\"ret\":0,\"response\":{\"message\":\"success\",\"data\":{\"list\":[{\"open\":\"4030\",\"high\":\"4065\",\"low\":\"3051\",\"close\":\"4035\",\"time\":\"1516174306\"},{\"open\":\"3050\",\"high\":\"4055\",\"low\":\"3021\",\"close\":\"4045\",\"time\":\"1516175306\"},{\"open\":\"3050\",\"high\":\"4055\",\"low\":\"4031\",\"close\":\"3045\",\"time\":\"1516176306\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"5041\",\"close\":\"4045\",\"time\":\"1516177306\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"3011\",\"close\":\"4045\",\"time\":\"1516178306\"}],\"data\":{\"goods_id\":1,\"type\":3}}},\"benchmark\":0}";
    private static final String line30="";
    private static final String line60 ="";
    private static final String hm ="{\"ret\":0,\"response\":{\"message\":\"success\",\"data\":{\"list\":[{\"open\":\"4030\",\"high\":\"4055\",\"low\":\"4001\",\"close\":\"4035\",\"time\":\"1516174306\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4021\",\"close\":\"4045\",\"time\":\"1516175306\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4031\",\"close\":\"4045\",\"time\":\"1516176306\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4041\",\"close\":\"4045\",\"time\":\"1516177306\"},{\"open\":\"4050\",\"high\":\"4055\",\"low\":\"4011\",\"close\":\"4045\",\"time\":\"1516178306\"}],\"data\":{\"goods_id\":1,\"type\":99}}},\"benchmark\":0}";
    public static String getLine5(){
        return line5;
    }
    public static String getLine15(){
        return line15;
    }
    public static String getHm(){
        return hm;
    }


}
