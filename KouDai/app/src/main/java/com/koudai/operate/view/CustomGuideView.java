package com.koudai.operate.view;

import android.view.View;

/**
 * Created by Administrator on 2016/10/15.
 */
public class CustomGuideView {
    public View view;
    public GuideView.Direction direction;
    public boolean isAbsolute;
    public int position;
    public int offsetX, offsetY;

    public CustomGuideView(View view, GuideView.Direction direction) {
        this.view = view;
        this.direction = direction;
        this.isAbsolute = false;
    }

    public CustomGuideView(View view, GuideView.Direction direction, int offsetX, int offsetY) {
        this.view = view;
        this.direction = direction;
        this.isAbsolute = false;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public CustomGuideView(View view, int direct) {
        this.view = view;
        this.position = direct;
        this.isAbsolute = true;
    }

    public CustomGuideView(View view, int direct, int offsetX, int offsetY) {
        this.view = view;
        this.position = direct;
        this.isAbsolute = false;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
