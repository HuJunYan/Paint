package com.koudai.operate.utils;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by m on 16-11-3.
 */
public class KLineDataUtil {

    private static KLineDataUtil mKLineDataUtil;

    private KLineDataUtil() {
    }

    private final int YAXIS_COUNT = 5;
    private String[] yAxisDataArry = new String[YAXIS_COUNT];
//    private mMinPrice
    public static synchronized KLineDataUtil getInstance() {
        if (mKLineDataUtil == null) {
            mKLineDataUtil = new KLineDataUtil();
        }
        return mKLineDataUtil;
    }

    public void setData(int min, int max) {
//        int newmax = (int)(((float)max)*11f/10f - (float)min/10f);
//        int newmin = (int)(((float)min)*11f/10f - (float)max/10f);
        int newmax = (int)(((float)max)*17f/16f - (float)min/16f);
        int newmin = (int)(((float)min)*17f/16f - (float)max/16f);
        for (int i = 0; i < YAXIS_COUNT; i++) {
            DecimalFormat decimalFormat = DefaultYAxisValueFormatter(0);
            Log.d("ret", "getData: " + i + " = " + decimalFormat.format(i));
            yAxisDataArry[i] = decimalFormat.format(newmin + (int)((float)i*(float)(float)(newmax-newmin)/(float)(YAXIS_COUNT-1)));
        }
    }

    public DecimalFormat DefaultYAxisValueFormatter(int digits) {

        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                b.append(".");
            b.append("0");
        }

        return  new DecimalFormat("###,###,###,##0" + b.toString());
    }

    public String[] getData() {
        return yAxisDataArry;
    }

    private boolean needDrawByHandFirstTime;

    public boolean getNeedDrawByHandFirstTime() {
        return needDrawByHandFirstTime;
    }

    public void setNeedDrawByHandFirstTime(boolean needDrawByHandFirstTime) {
        this.needDrawByHandFirstTime = needDrawByHandFirstTime;
    }
    private int mKChartHeight = 513;

    public int getmKChartHeight() {
        return mKChartHeight;
    }

    public void setmKChartHeight(int mKChartHeight) {
        this.mKChartHeight = mKChartHeight;
    }

}
