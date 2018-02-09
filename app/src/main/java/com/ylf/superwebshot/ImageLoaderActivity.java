package com.ylf.superwebshot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ylf.imageloader.cache.DoubleCache;
import com.ylf.imageloader.config.ImageLoaderConfig;
import com.ylf.imageloader.loader.SimpleImageLoader;
import com.ylf.imageloader.policy.ReversePolicy;

import butterknife.BindView;

public class ImageLoaderActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SimpleImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setThreadCount(3)
                .setLoaderPolicy(new ReversePolicy())
                .setCachePolicy(new DoubleCache(this))
                .setLoadingImage(R.mipmap.ic_launcher)
                .setFaildImage(R.mipmap.ic_launcher_round)
                .build();
        imageLoader = SimpleImageLoader.getInstance(config);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);



    }
}
