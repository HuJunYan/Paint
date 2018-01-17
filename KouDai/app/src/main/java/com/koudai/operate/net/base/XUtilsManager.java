package com.koudai.operate.net.base;

import android.content.Context;

import com.koudai.operate.utils.Utils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;

public class XUtilsManager {
    private static XUtilsManager mXUtilsManager;
    private BitmapUtils mBitmapUtils;
    private HttpUtils mHttpUtils;
    private DbUtils mDbUtils;

    private XUtilsManager(Context e) {
        mHttpUtils = new HttpUtils(30000);
        mBitmapUtils = BitmapUtils.create(e);
        mBitmapUtils.configDiskCachePath(Utils.getCachePath(e));
        mDbUtils = DbUtils.create(e);
    }

    public static XUtilsManager getInstance(Context f) {
        if (null == mXUtilsManager) {
            synchronized (XUtilsManager.class) {
                if (null == mXUtilsManager) {
                    mXUtilsManager = new XUtilsManager(f);
                }
            }
        }
        return mXUtilsManager;
    }

    public BitmapUtils getBitmapUtils() {
        return mBitmapUtils;
    }

    public HttpUtils getHttpUtils() {
        return mHttpUtils;
    }

    public DbUtils getDbUtils() {
        return mDbUtils;
    }
}