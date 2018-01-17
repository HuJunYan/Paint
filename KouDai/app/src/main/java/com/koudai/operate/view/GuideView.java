package com.koudai.operate.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.koudai.operate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouweixian on 2016/1/23.
 */
public class GuideView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private final String TAG = getClass().getSimpleName();
    private Context mContent;
    private boolean first = true;
    /**
     * targetView前缀。SHOW_GUIDE_PREFIX + targetView.getId()作为保存在SP文件的key。
     */
    private static final String SHOW_GUIDE_PREFIX = "show_guide_on_view_";

    /**
     * targetView 的外切圆半径
     */
    private int radius;

    private int radiusX;
    private int radiusY;
    /**
     * 需要显示提示信息的View
     */
    private View targetView;
    /**
     * 自定义View
     */
    private List<CustomGuideView> customList = new ArrayList<>();
    /**
     * 透明圆形画笔
     */
    private Paint mCirclePaint;
    /**
     * 背景色画笔
     */
    private Paint mBackgroundPaint;
    /**
     * targetView是否已测量
     */
    private boolean isMeasured;
    /**
     * targetView圆心
     */
    private int[] center;
    /**
     * 绘图层叠模式
     */
    private PorterDuffXfermode porterDuffXfermode;
    /**
     * 绘制前景bitmap
     */
    private Bitmap bitmap;
    /**
     * 背景色和透明度，格式 #aarrggbb
     */
    private int backgroundColor;
    /**
     * Canvas,绘制bitmap
     */
    private Canvas temp;

    /**
     * 形状
     */
    private MyShape myShape;
    /**
     * targetView左上角坐标
     */
    private int[] location;
    private boolean onClickExit;
    private OnClickCallback onclickListener;
    private RelativeLayout guideViewLayout;

    public void restoreState() {
        Log.v(TAG, "restoreState");
        mCirclePaint = null;
        mBackgroundPaint = null;
        isMeasured = false;
        center = null;
        porterDuffXfermode = null;
        bitmap = null;
        needDraw = true;
        //        backgroundColor = Color.parseColor("#00000000");
        temp = null;
        //        direction = null;

    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public GuideView(Context context) {
        super(context);
        this.mContent = context;
        init();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setShape(MyShape shape) {
        this.myShape = shape;
    }

    public void setCustomGuideView(CustomGuideView customGuideView) {
        this.customList.add(customGuideView);
        if (!first) {
            restoreState();
        }
    }

    public void setBgColor(int background_color) {
        this.backgroundColor = background_color;
    }

    public View getTargetView() {
        return targetView;
    }

    public void setTargetView(View targetView) {
        this.targetView = targetView;
        //        restoreState();
        if (!first) {
            //            guideViewLayout.removeAllViews();
        }
    }

    private void init() {
    }

    public void showOnce() {
        if (targetView != null) {
            mContent.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putBoolean(generateUniqId(targetView), true).commit();
        }
    }

    private boolean hasShown() {
        if (targetView == null)
            return true;
        return mContent.getSharedPreferences(TAG, Context.MODE_PRIVATE).getBoolean(generateUniqId(targetView), false);
    }

    private String generateUniqId(View v) {
        return SHOW_GUIDE_PREFIX + v.getId();
    }

    public int[] getCenter() {
        return center;
    }

    public void setCenter(int[] center) {
        this.center = center;
    }

    public void hide() {
        Log.v(TAG, "hide");
        for (CustomGuideView view : customList) {
            if (view != null) {
                targetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                this.removeAllViews();
                ((FrameLayout) ((Activity) mContent).getWindow().getDecorView()).removeView(this);
                restoreState();
            }
        }

    }

    public void show() {
        Log.v(TAG, "show");
        if (hasShown())
            return;

        if (targetView != null) {
            targetView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        this.setBackgroundResource(R.color.transparent0);

        ((FrameLayout) ((Activity) mContent).getWindow().getDecorView()).addView(this);
        first = false;
    }

    /**
     * 添加提示文字，位置在targetView的下边
     * 在屏幕窗口，添加蒙层，蒙层绘制总背景和透明圆形，圆形下边绘制说明文字
     */
    private void createGuideView() {
        Log.v(TAG, "createGuideView");

        // 添加到蒙层
        //        if (guideViewLayout == null) {
        //            guideViewLayout = new RelativeLayout(mContent);
        //        }
        for (CustomGuideView customView : customList) {
            // Tips布局参数
            LayoutParams guideViewParams;
            guideViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            guideViewParams.setMargins(0, center[1] + radius + 10, 0, 0);
            if (customView.direction != null && !customView.isAbsolute) {
                int width = this.getWidth();
                int height = this.getHeight();

                int offsetX = customView.offsetX;
                int offsetY = customView.offsetY;
                int left = center[0] - radiusX;
                int right = center[0] + radiusX;
                int top = center[1] - radiusY;
                int bottom = center[1] + radiusY;
                switch (customView.direction) {
                    case TOP:
                        customView.position = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        guideViewParams.setMargins(offsetX, offsetY - height + top, -offsetX, height - top - offsetY);
                        break;
                    case LEFT:
                        customView.position = Gravity.RIGHT;
                        guideViewParams.setMargins(offsetX - width + left, top + offsetY, width - left - offsetX, -top - offsetY);
                        break;
                    case BOTTOM:
                        customView.position = Gravity.CENTER_HORIZONTAL;
                        guideViewParams.setMargins(offsetX, bottom + offsetY, -offsetX, -bottom - offsetY);
                        break;
                    case RIGHT:
                        customView.position = Gravity.LEFT;
                        guideViewParams.setMargins(right + offsetX, top + offsetY, -right - offsetX, -top - offsetY);
                        break;
                    case LEFT_TOP:
                        customView.position = Gravity.RIGHT | Gravity.BOTTOM;
                        guideViewParams.setMargins(offsetX - width + left, offsetY - height + top, width - left - offsetX, height - top - offsetY);
                        break;
                    case LEFT_BOTTOM:
                        customView.position = Gravity.RIGHT | Gravity.TOP;
                        guideViewParams.setMargins(offsetX - width + left, bottom + offsetY, width - left - offsetX, -bottom - offsetY);
                        break;
                    case RIGHT_TOP:
                        customView.position = Gravity.BOTTOM;
                        guideViewParams.setMargins(right + offsetX, offsetY - height + top, -right - offsetX, height - top - offsetY);
                        break;
                    case RIGHT_BOTTOM:
                        guideViewParams.setMargins(right + offsetX, bottom + offsetY, -right - offsetX, -top - offsetY);
                        break;
                }
            }
            guideViewParams.gravity = customView.position;
            this.addView(customView.view, guideViewParams);
        }
    }

    /**
     * 获得targetView 的宽高，如果未测量，返回｛-1， -1｝
     *
     * @return
     */
    private int[] getTargetViewSize() {
        int[] location = {-1, -1};
        if (isMeasured) {
            location[0] = targetView.getWidth();
            location[1] = targetView.getHeight();
        }
        return location;
    }

    /**
     * 获得targetView 的半径
     *
     * @return
     */
    private int getTargetViewRadius() {
        if (isMeasured) {
            int[] size = getTargetViewSize();
            int x = size[0];
            int y = size[1];
            switch (myShape) {
                case CIRCULAR://圆形
                    radiusX = (int) (Math.sqrt(x * x + y * y) / 2);
                    radiusY = (int) (Math.sqrt(x * x + y * y) / 2);
                    break;
                case ELLIPSE://椭圆
                    //RectF对象
                    radiusX = x / 2 + 20;                              //左边
                    radiusY = y / 2 + 20;                                   //上边
                    break;
                case RECTANGULAR://圆角矩形
                    //RectF对象
                    radiusX = x / 2 + 5;                              //左边
                    radiusY = y / 2 + 5;                     //绘制圆角矩形
                    break;
                case RECT://矩形
                    //RectF对象
                    radiusX = x / 2 + 5;                              //左边
                    radiusY = y / 2 + 5;                   //绘制圆角矩形
                    break;
                default:
                    radiusX = (int) (Math.sqrt(x * x + y * y) / 2);
                    radiusY = (int) (Math.sqrt(x * x + y * y) / 2);
                    break;
            }
            return (int) (Math.sqrt(x * x + y * y) / 2);
        }

        return -1;
    }

    boolean needDraw = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v(TAG, "onDraw");

        if (!isMeasured)
            return;

        if (targetView == null)
            return;

        //        if (!needDraw) return;

        drawBackground(canvas);

    }

    private void drawBackground(Canvas canvas) {
        Log.v(TAG, "drawBackground");
        needDraw = false;
        // 先绘制bitmap，再将bitmap绘制到屏幕
        bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        temp = new Canvas(bitmap);

        // 背景画笔
        Paint bgPaint = new Paint();
        if (backgroundColor != 0)
            bgPaint.setColor(backgroundColor);
        else
            bgPaint.setColor(Color.parseColor("#e8000000"));
        // 绘制屏幕背景
        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), bgPaint);

        // targetView 的透明圆形画笔
        if (mCirclePaint == null)
            mCirclePaint = new Paint();
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);// 或者CLEAR
        mCirclePaint.setXfermode(porterDuffXfermode);
        mCirclePaint.setAntiAlias(true);

        if (myShape != null) {
            RectF oval = new RectF();
            switch (myShape) {
                case CIRCULAR://圆形
                    temp.drawCircle(center[0], center[1], radius, mCirclePaint);//绘制圆形
                    break;
                case ELLIPSE://椭圆
                    //RectF对象
                    oval.left = center[0] - targetView.getWidth() / 2 - 20;                              //左边
                    oval.top = center[1] - targetView.getHeight() / 2 - 20;                                   //上边
                    oval.right = center[0] + targetView.getWidth() / 2 + 20;                             //右边
                    oval.bottom = center[1] + targetView.getHeight() / 2 + 20;                                 //下边
                    temp.drawOval(oval, mCirclePaint);                   //绘制椭圆
                    break;
                case RECTANGULAR://圆角矩形
                    //RectF对象
                    oval.left = center[0] - targetView.getWidth() / 2 - 5;                              //左边
                    oval.top = center[1] - targetView.getHeight() / 2 - 5;                                   //上边
                    oval.right = center[0] + targetView.getWidth() / 2 + 5;                             //右边
                    oval.bottom = center[1] + targetView.getHeight() / 2 + 5;                               //下边
                    temp.drawRoundRect(oval, radius, radius, mCirclePaint);                   //绘制圆角矩形
                    break;
                case RECT://圆角矩形
                    //RectF对象
                    oval.left = center[0] - targetView.getWidth() / 2 - 5;                              //左边
                    oval.top = center[1] - targetView.getHeight() / 2 - 5;                                   //上边
                    oval.right = center[0] + targetView.getWidth() / 2 + 5;                             //右边
                    oval.bottom = center[1] + targetView.getHeight() / 2 + 5;                                //下边
                    temp.drawRect(oval, mCirclePaint);                   //绘制圆角矩形
                    break;
            }
        } else {
            temp.drawCircle(center[0], center[1], radius, mCirclePaint);//绘制圆形
        }

        // 绘制到屏幕
        canvas.drawBitmap(bitmap, 0, 0, bgPaint);
        bitmap.recycle();
    }

    public void setOnClickExit(boolean onClickExit) {
        this.onClickExit = onClickExit;
    }

    public void setOnclickListener(OnClickCallback onclickListener) {
        this.onclickListener = onclickListener;
    }

    private void setClickInfo() {
        final boolean exit = onClickExit;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null) {
                    onclickListener.onClickedGuideView();
                }
                if (exit) {
                    hide();
                }
            }
        });
    }

    @Override
    public void onGlobalLayout() {
        if (isMeasured)
            return;
        if (targetView.getHeight() > 0 && targetView.getWidth() > 0) {
            isMeasured = true;
        }

        // 获取targetView的中心坐标
        if (center == null) {
            // 获取右上角坐标
            location = new int[2];
            targetView.getLocationInWindow(location);
            center = new int[2];
            // 获取中心坐标
            center[0] = location[0] + targetView.getWidth() / 2;
            center[1] = location[1] + targetView.getHeight() / 2;
        }
        // 获取targetView外切圆半径
        if (radius == 0) {
            radius = getTargetViewRadius();
        }
        // 添加GuideView
        createGuideView();
    }

    /**
     * 定义GuideView相对于targetView的方位，共八种。不设置则默认在targetView下方
     */
    public enum Direction {
        LEFT, TOP, RIGHT, BOTTOM,
        LEFT_TOP, LEFT_BOTTOM,
        RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 定义目标控件的形状，共3种。圆形，椭圆，带圆角的矩形（可以设置圆角大小），不设置则默认是圆形
     */
    public enum MyShape {
        CIRCULAR, ELLIPSE, RECTANGULAR, RECT
    }

    /**
     * GuideView点击Callback
     */
    public interface OnClickCallback {
        void onClickedGuideView();
    }

    public static class Builder {
        static GuideView guiderView;
        static Builder instance = new Builder();
        Context mContext;

        private Builder() {
        }

        public Builder(Context ctx) {
            mContext = ctx;
        }

        public static Builder newInstance(Context ctx) {
            guiderView = new GuideView(ctx);
            return instance;
        }

        public Builder setTargetView(View target) {
            guiderView.setTargetView(target);
            return instance;
        }

        public Builder setBgColor(int color) {
            guiderView.setBgColor(color);
            return instance;
        }

        public Builder setShape(MyShape shape) {
            guiderView.setShape(shape);
            return instance;
        }

        public Builder setRadius(int radius) {
            guiderView.setRadius(radius);
            return instance;
        }

        public Builder addCustomGuideView(CustomGuideView view) {
            guiderView.setCustomGuideView(view);
            return instance;
        }

        public Builder setCenter(int X, int Y) {
            guiderView.setCenter(new int[]{X, Y});
            return instance;
        }

        public Builder showOnce() {
            guiderView.showOnce();
            return instance;
        }

        public GuideView build() {
            guiderView.setClickInfo();
            return guiderView;
        }

        public Builder setOnclickExit(boolean onclickExit) {
            guiderView.setOnClickExit(onclickExit);
            return instance;
        }

        public Builder setOnclickListener(final OnClickCallback callback) {
            guiderView.setOnclickListener(callback);
            return instance;
        }
    }

}
