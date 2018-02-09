package com.ylf.superwebshot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShotWebAct extends AppCompatActivity {
    public static final String WEB_URL = "web_url";

    @BindView(R.id.scrollView) ScrollView scrollView;
    @BindView(R.id.webView)WebView webView;
    @BindView(R.id.act_shot_web_progress)ProgressBar progressBar;

    private Unbinder unbinder;
    private String webUrl;

    public static void goToIntent(Context context, String url){
        Intent intent = new Intent(context, ShotWebAct.class);
        Bundle bundle = new Bundle();
        bundle.putString(WEB_URL, url == null ? "" : url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shot_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        unbinder = ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        webUrl = bundle.getString(WEB_URL, "");

        initWebView();

        webView.loadUrl(webUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_shot_screen) {
            return true;
        }
        if (id == R.id.action_shot_web) {
            drawScrollView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void drawScrollView(){
        int h = 0;
        for(int i = 0; i < scrollView.getChildCount(); i++){
            h += scrollView.getChildAt(i).getHeight();
        }
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        String imagePath = "";
        try {
            imagePath = initImagePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmapToFile(bitmap, imagePath);
    }

    private void bitmapToFile(Bitmap bitmap, String imagePath){
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String initImagePath() throws IOException {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String imagePath = Constants.Directories.getWebLongPath();
            if(TextUtils.isEmpty(imagePath)){
                return "";
            }else {
                return imagePath;
            }
        }else{
            return "";
        }
    }

    private void initWebView(){
        WebView webView = this.webView;
        WebUtil.initWebView(webView);
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    public void Shot(View view) {

    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressBar != null) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
