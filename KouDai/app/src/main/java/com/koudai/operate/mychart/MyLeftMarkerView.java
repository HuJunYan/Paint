package com.koudai.operate.mychart;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.koudai.operate.R;

import java.text.DecimalFormat;

/**
 * author：ajiang
 * mail：1025065158@qq.com
 * blog：http://blog.csdn.net/qqyanjiang
 */
public class MyLeftMarkerView extends MarkerView {
    private Context mContext;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private float num;
    private DecimalFormat mFormat;
    public MyLeftMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mContext = context;
        mFormat=new DecimalFormat("#0.0");
        markerTv = (TextView) findViewById(R.id.marker_tv);
        markerTv.setTextSize(10);
    }

    public void setData(float num){

        this.num=num;
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        markerTv.setText(mFormat.format(num));
    }

    public int getWindowWidth(){
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
//        float density1 = dm.density;
        int width = dm.widthPixels;
//        int height3 = dm.heightPixels;
        return width;
    }
    @Override
    public int getXOffset(float xpos) {
        return getWindowWidth();
    }

    @Override
    public int getYOffset(float ypos) {
        return 0;
    }
}
