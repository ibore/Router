package me.ibore.router.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.ibore.router.XRouter;
import me.ibore.router.RouterConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XRouter.init(RouterConfig.get().debug(BuildConfig.DEBUG).schemes("demo")
        .domains("ibore.me"));
    }
}
