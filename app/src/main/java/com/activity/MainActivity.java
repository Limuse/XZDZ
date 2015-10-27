package com.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.custom.SlidingMenu;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

public class MainActivity extends LActivity {

    private SlidingMenu mMenu;
    protected void onLCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
    }

    public void toggleMenu(View view)
    {
        T.ss("aaaa");
    }
}
