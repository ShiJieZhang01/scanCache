package com.example.admin.sacncachedemo;

import android.graphics.drawable.Drawable;

/**
 * Created by admin on 17/3/18.
 */

public class CacheListItem {
    private long mCacheSize;
    private String mPackageName, mApplicationName;
    private Drawable mIcon;

    public CacheListItem(String packageName, String applicationName, Drawable icon, long cacheSize) {
        mCacheSize = cacheSize;
        mPackageName = packageName;
        mApplicationName = applicationName;
        mIcon = icon;
    }

    public Drawable getApplicationIcon() {
        return mIcon;
    }

    public String getApplicationName() {
        return mApplicationName;
    }

    public long getCacheSize() {
        return mCacheSize;
    }

    public String getPackageName() {
        return mPackageName;
    }
}
