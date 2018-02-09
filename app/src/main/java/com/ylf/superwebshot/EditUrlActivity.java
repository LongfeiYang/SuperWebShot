package com.ylf.superwebshot;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditUrlActivity extends AppCompatActivity {
    @BindView(R.id.et_edit_url)EditText editText;

    private Unbinder unbinder;

    public static void goToIntent(Context context){
        Intent intent = new Intent(context, EditUrlActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_url);

        unbinder = ButterKnife.bind(this);
    }

    public void goToShot(View view) {
        KeyboardUtil.hideSoftInput(EditUrlActivity.this);
        String webUrl = editText.getText().toString();
        if(TextUtils.isEmpty(webUrl)){
            showSnackBar(editText.getRootView(), "网址不能为空！");
            return;
        }
        ShotWebAct.goToIntent(EditUrlActivity.this, webUrl);
    }

    public void showSnackBar(View view, String content){

        Snackbar.make(view, content, Snackbar.LENGTH_LONG)
        .setAction("Action", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.requestFocus();
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
