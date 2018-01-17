package com.koudai.operate.mychart;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.koudai.operate.R;
import com.koudai.operate.model.KLineItemBean;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by m on 16-10-13.
 */
public class MyKChartMarkerView extends MarkerView {
    private float mCurrentX;
    private float mCurrentY;
    private float viewHeight;
    private float viewWidth;
    private float mChange;
    private String mTime;
    private Context mContext;
    private TextView mk_high;
    private TextView mk_low;
    private TextView mk_open;
    private TextView mk_close;
    private TextView mk_time;
    private RelativeLayout rl_kline_marker;
    private KLineItemBean mKLineItemBean;
    public MyKChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mContext = context;
        mk_high = (TextView) findViewById(R.id.mk_high);
        mk_low = (TextView) findViewById(R.id.mk_low);
        mk_open = (TextView) findViewById(R.id.mk_open);
        mk_close = (TextView) findViewById(R.id.mk_close);
        mk_time = (TextView) findViewById(R.id.mk_time);
        rl_kline_marker = (RelativeLayout) findViewById(R.id.rl_kline_marker);
//        mFormat=new DecimalFormat("#0.0");
//        markerTv = (TextView) findViewById(R.id.marker_tv);
//        markerTv.setTextSize(10);
    }

    public void setData(KLineItemBean kLineItemBean){
        this.mKLineItemBean = kLineItemBean;
    }

    public void setChartViewHeight(float viewHeight) {
        this.viewHeight = viewHeight;
        Log.d("ret", "==========viewHeight = " + viewHeight);
    }

    public void setChartViewWidth(float viewWidth) {
        this.viewWidth = viewWidth;
    }

    public void setCurrentPosition(float currentX, float currentY) {
        this.mCurrentX = currentX;
        this.mCurrentY = currentY;
        Log.d("ret", "==========x = " + mCurrentX + " ; y = " + mCurrentY);
    }

    public void setChanged(float change) {
        this.mChange = change;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mk_time.setText(mKLineItemBean.getTime());
        mk_high.setText(mKLineItemBean.getHigh());
        mk_low.setText(mKLineItemBean.getLow());
        mk_open.setText(mKLineItemBean.getOpen());
        mk_close.setText(mKLineItemBean.getClose());
//        NumberFormat nf = NumberFormat.getPercentInstance();
//        nf.setMaximumFractionDigits(2);
//        String changePercentage = nf.format(Double.valueOf(String.valueOf(mChange)));
//        mk_changed.setText("盈亏:"+changePercentage);
    }

    @Override
    public int getXOffset(float xpos) {
        if (mCurrentX < viewWidth*0.38) {
            return (int) viewWidth-25;
        } else {
            return rl_kline_marker.getWidth()+5;
        }
    }

    @Override
    public int getYOffset(float ypos) {
        return 0;
    }

//    @Override
//    public int getYOffset(float ypos) {
//        int posy = 0;
//        if (mCurrentY < viewHeight/4) {
//            posy = (int)(viewHeight)/5;
//        } else if (mCurrentY < viewHeight/3) {
//            posy = (int)(viewHeight)/6;
//        } else if (mCurrentY < viewHeight*5/12) {
//            posy = (int)(viewHeight)/7;
//        } else if (mCurrentY > viewHeight*3/4) {
//            posy = -(int)(viewHeight)/5;
//        }  else if (mCurrentY < viewHeight*2/3) {
//            posy = -(int)(viewHeight)/6;
//        } else if (mCurrentY < viewHeight*7/12) {
//            posy = -(int)(viewHeight)/7;
//        } else {
//            posy = 0;
//        }
//
//        return posy;
//    }
}
