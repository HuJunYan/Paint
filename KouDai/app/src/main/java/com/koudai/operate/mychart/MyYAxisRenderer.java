package com.koudai.operate.mychart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;

import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.koudai.operate.utils.KLineDataUtil;

/**
 * author：ajiang
 * mail：1025065158@qq.com
 * blog：http://blog.csdn.net/qqyanjiang
 *
 * 重写y轴labels
 */
public class MyYAxisRenderer extends YAxisRenderer {
    protected MyYAxis mYAxis;
    public MyYAxisRenderer(ViewPortHandler viewPortHandler, MyYAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
        mYAxis = yAxis;
    }

    @Override
    protected void computeAxisValues(float min, float max) {
        /*只显示最大最小情况下*/
        if (mYAxis.isShowOnlyMinMaxEnabled()) {
            mYAxis.mEntryCount = 2;
            mYAxis.mEntries = new float[2];
            mYAxis.mEntries[0] = min;
            mYAxis.mEntries[1] = max;
            return;
        }
        /*折线图左边没有basevalue，则调用系统*/
        if (Float.isNaN(mYAxis.getBaseValue())) {
            super.computeAxisValues(min, max);
            return;
        }
        float base = mYAxis.getBaseValue();
        float yMin = min;
        int labelCount = mYAxis.getLabelCount();
        float interval = (base - yMin) / labelCount;
        int n = labelCount * 2 + 1;
        mYAxis.mEntryCount = n;
        mYAxis.mEntries = new float[n];
        int i;
        float f;
        for (f = min, i = 0; i < n; f += interval, i++) {
            mYAxis.mEntries[i] = f;
//            Log.d("resultresult", "y"+i+"="+f);
        }
    }
    @Override
    protected void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {
       /*当有最小text的时候*/
        if (!TextUtils.isEmpty(mYAxis.getMinValue()) && mYAxis.isShowOnlyMinMaxEnabled()) {
//            Log.d("resultresult", "drawYLabels == 0");
            for (int i = 0; i < mYAxis.mEntryCount; i++) {
                /*获取对应位置的值*/
                String text = mYAxis.getFormattedLabel(i);
                if (i == 0) {
                    text = mYAxis.getMinValue();
                }
                if (i == 1) {
                    c.drawText(text, fixedPosition, mViewPortHandler.offsetTop()+2*offset+5 , mAxisLabelPaint);
                } else if (i == 0) {
                    c.drawText(text, fixedPosition, mViewPortHandler.contentBottom() - 3, mAxisLabelPaint);
                }
            }
        }
        else {
            if (KLineDataUtil.getInstance().getNeedDrawByHandFirstTime()) {
                needDrawByHandFirstTime(c, fixedPosition, positions, offset);
            }else {
                notNeedDrawByHandFirstTime(c, fixedPosition, positions, offset);
            }
        }
    }

    private void notNeedDrawByHandFirstTime(Canvas c, float fixedPosition, float[] positions, float offset) {
        for (int i = 0; i < mYAxis.mEntryCount; i++) {
            String[] yAxisArray = KLineDataUtil.getInstance().getData();
            String text = mYAxis.getFormattedLabel(i);
            if (!mYAxis.isDrawTopYLabelEntryEnabled() && i >= mYAxis.mEntryCount - 1)
                return;

            int labelHeight = Utils.calcTextHeight(mAxisLabelPaint, text);
            float pos = positions[i * 2 + 1] + offset;

            if ((pos - labelHeight) < mViewPortHandler.contentTop()) {

                pos = mViewPortHandler.contentTop() + offset * 2.5f + 3;
            } else if ((pos + labelHeight / 2) > mViewPortHandler.contentBottom()) {
                pos = mViewPortHandler.contentBottom() - 3;
            }
            c.drawText(text, fixedPosition, pos, mAxisLabelPaint);
        }
    }

    private void needDrawByHandFirstTime(Canvas c, float fixedPosition, float[] positions, float offset) {
        float lastPos = 0;
        int mKChartHeight = KLineDataUtil.getInstance().getmKChartHeight();
        boolean isPositionEqual = false;
        for (int i = 0; i < mYAxis.mEntryCount; i++) {
            String[] yAxisArray = KLineDataUtil.getInstance().getData();
            String text = mYAxis.getFormattedLabel(i);
            if (!mYAxis.isDrawTopYLabelEntryEnabled() && i >= mYAxis.mEntryCount - 1)
                return;

            int labelHeight = Utils.calcTextHeight(mAxisLabelPaint, text);
            float pos = positions[i * 2 + 1] + offset;

            if ((pos - labelHeight) < mViewPortHandler.contentTop()) {

                pos = mViewPortHandler.contentTop() + offset * 2.5f + 3;
            } else if ((pos + labelHeight / 2) > mViewPortHandler.contentBottom()) {
                pos = mViewPortHandler.contentBottom() - 3;
            }
            if (i == 0) {
                lastPos = pos;
                pos = mKChartHeight - labelHeight/2;
                isPositionEqual = false;
            } else {
                float range = Math.abs(lastPos - pos);
                if (range < 0.01f) {
//                        myKChart.calcMinMaxByHand();
                    String text1 = mYAxis.getFormattedLabel(i);

                    Paint mAxisLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mAxisLinePaint.setColor(Color.WHITE);
                    mAxisLinePaint.setTypeface(mYAxis.getTypeface());
                    mAxisLinePaint.setTextSize(mYAxis.getTextSize());
                    c.drawText(mYAxis.getFormattedLabel(0), fixedPosition, mKChartHeight - labelHeight/2, mAxisLinePaint);
                    c.drawText(yAxisArray[0], fixedPosition, mKChartHeight - labelHeight/2, mAxisLabelPaint);
                    isPositionEqual = true;
                }
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                        pos = mKChartHeight*(mYAxis.mEntryCount-1-i)/4 + labelHeight/2;
                        break;
                    case 4:
                        pos = labelHeight;
                        break;
                }
            }
            if (isPositionEqual) {
                c.drawText(yAxisArray[i], fixedPosition, pos, mAxisLabelPaint);
            } else {
                c.drawText(text, fixedPosition, pos, mAxisLabelPaint);
            }
        }
    }

}