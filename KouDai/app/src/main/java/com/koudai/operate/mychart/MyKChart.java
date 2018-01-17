package com.koudai.operate.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.koudai.operate.model.KLineItemBean;
import com.koudai.operate.model.KLineListBean;

import java.util.List;

/**
 * Created by m on 16-10-13.
 */
public class MyKChart extends CombinedChart {
    private float mDeltaY;
    private MyKChartMarkerView mMyKChartMarkerView;
    private List<KLineItemBean> mKLineListBeanList;

    public MyKChart(Context context) {
        super(context);
    }

    public MyKChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyKChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mAxisLeft = new MyYAxis(YAxis.AxisDependency.LEFT);
        mAxisRendererLeft = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisLeft, mLeftAxisTransformer);
    }
    public void setMarker(MyKChartMarkerView myKChartMarkerView, MyRightMarkerView markerRight, List<KLineItemBean> kLineListBeanList) {
        this.mMyKChartMarkerView = myKChartMarkerView;
//        this.myMarkerViewRight = markerRight;
        this.mKLineListBeanList = kLineListBeanList;
    }


    @Override
    public YAxis getAxisLeft() {
        return super.getAxisLeft();
    }

    @Override
    public YAxis getAxisRight() {
        return super.getAxisRight();
    }

    @Override
    public YAxis getAxis(YAxis.AxisDependency axis) {
        return super.getAxis(axis);
    }

    public void setHighlightValue(Highlight h) {
        if (mData == null)
            mIndicesToHighlight = null;
        else {
            mIndicesToHighlight = new Highlight[]{
                    h};
        }
        invalidate();
    }

    public void setPositionData(float viewHeight, float viewWidth, float currentX, float currentY) {
        if (mMyKChartMarkerView != null) {
            mMyKChartMarkerView.setChartViewHeight(viewHeight);
            mMyKChartMarkerView.setChartViewWidth(viewWidth);
            mMyKChartMarkerView.setCurrentPosition(currentX, currentY);
//            mMyKChartMarkerView.setTime(time);
//            mMyKChartMarkerView.setChanged(change);
        }
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        try {
            if (!mDrawMarkerViews || !valuesToHighlight())
                return;
            for (int i = 0; i < mIndicesToHighlight.length; i++) {
                Highlight highlight = mIndicesToHighlight[i];
                int xIndex = mIndicesToHighlight[i].getXIndex();
                int dataSetIndex = mIndicesToHighlight[i].getDataSetIndex();
                float deltaX = mXAxis != null
                        ? mXAxis.mAxisRange
                        : ((mData == null ? 0.f : mData.getXValCount()) - 1.f);
                if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {
                    Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
                    // make sure entry not null
                    if (e == null || e.getXIndex() != mIndicesToHighlight[i].getXIndex())
                        continue;
                    float[] pos = getMarkerPosition(e, highlight);
                    // check bounds
                    if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                        continue;

                    KLineItemBean kLineItemBean = mKLineListBeanList.get(mIndicesToHighlight[0].getXIndex());
                    mMyKChartMarkerView.setData(kLineItemBean);
                    mMyKChartMarkerView.refreshContent(e, mIndicesToHighlight[i]);

                    /*重新计算大小*/
                    mMyKChartMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    mMyKChartMarkerView.layout(0, 0, mMyKChartMarkerView.getMeasuredWidth(),
                            mMyKChartMarkerView.getMeasuredHeight());

                    mMyKChartMarkerView.draw(canvas, mViewPortHandler.contentLeft() - mMyKChartMarkerView.getWidth(), 0f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
