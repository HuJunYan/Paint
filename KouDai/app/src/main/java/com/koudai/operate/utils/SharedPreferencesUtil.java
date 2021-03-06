package com.koudai.operate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	private static SharedPreferencesUtil mSharedPreferencesUtil;
	private static SharedPreferences mSharedPreferences;

	private SharedPreferencesUtil(Context context) {
		mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
	}

	public static synchronized SharedPreferencesUtil getInstance(Context context) {
		if (mSharedPreferencesUtil == null) {
			mSharedPreferencesUtil = new SharedPreferencesUtil(context);
		}
		return mSharedPreferencesUtil;
	}

	public String getString(String key) {
		return mSharedPreferences.getString(key, "");
	}

	public String getString(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	public void putString(String key, String value) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public int getInt(String key) {
		return mSharedPreferences.getInt(key, 0);
	}

	public void putInt(String key, int value) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	public long getLong(String key) {
		return mSharedPreferences.getLong(key, 0);
	}

	public void putLong(String key, long value) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(key, value);
		edit.commit();
	}

	public Boolean getBoolean(String key) {
		return mSharedPreferences.getBoolean(key, false);
	}

	public void putBoolean(String key, boolean value) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	/**
	 * 通过key进行删除
	 * 
	 * @param key
	 */
	public void remove(String key) {
		Editor edit = mSharedPreferences.edit();
		edit.remove(key);
		edit.commit();
	}
}
