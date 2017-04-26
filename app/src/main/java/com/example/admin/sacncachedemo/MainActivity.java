package com.example.admin.sacncachedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CleanerService.OnActionListener{

    private CleanerService mCleanerService;
    List<CacheListItem> mCacheListItem = new ArrayList<>();
    RublishMemoryAdapter rublishMemoryAdapter;
    ListView listView_cache;
    TextView textView;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("TAG","zsj service");
            mCleanerService = ((CleanerService.CleanerServiceBinder) service).getService();
            mCleanerService.setOnActionListener(MainActivity.this);

            mCleanerService.scanCache();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCleanerService.setOnActionListener(null);
            mCleanerService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView_cache = (ListView) findViewById(R.id.listview_cache);
        textView = (TextView) findViewById(R.id.textView);
        rublishMemoryAdapter = new RublishMemoryAdapter(this , mCacheListItem);
        listView_cache.setAdapter(rublishMemoryAdapter);
        listView_cache.setOnItemClickListener(rublishMemoryAdapter);
        bindService(new Intent(MainActivity.this, CleanerService.class),
                mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onScanStarted(Context context) {

    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max) {

    }

    @Override
    public void onScanCompleted(Context context, List<CacheListItem> apps) {
        Log.d("TAG"," apps "+apps);

        mCacheListItem.clear();
        mCacheListItem.addAll(apps);
        rublishMemoryAdapter.notifyDataSetChanged();
        if (apps.size() > 0) {

            long medMemory = mCleanerService != null ? mCleanerService.getCacheSize() : 0;

            StorageSize mStorageSize = StorageUtil.convertStorageSize(medMemory);


            Log.d("TAG","zsj"+mStorageSize.value+mStorageSize.suffix);
            textView.setText(mStorageSize.value+mStorageSize.suffix);
            //  textCounter.setSuffix(mStorageSize.suffix);
        } else {
            Log.d("TAG","zsj else");
        }


    }

    @Override
    public void onCleanStarted(Context context) {

    }

    @Override
    public void onCleanCompleted(Context context, long cacheSize) {

    }
}
