package com.wenym.grooo.ui.activity;

import android.os.Bundle;

import com.runzii.lib.ui.base.BaseActivity;
import com.wenym.grooo.R;
import com.wenym.grooo.ui.fragments.AboutUsFragment;

/**
 * Created by runzii on 15-11-1.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected boolean isEnableSwipe() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected boolean isTranslucentStatus() {
        return false;
    }

    @Override
    protected boolean isDisplayHomeAsUp() {
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new AboutUsFragment()).commit();
    }
}
