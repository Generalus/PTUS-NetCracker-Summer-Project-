package ru.thesn.ptus.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import ru.thesn.ptus.app.udm.DatabaseHelper;
import ru.thesn.ptus.app.udm.HelperFactory;

/**
 * Created by Никита on 14.06.2015.
 */
public class MyApplication extends Application {
    public static DatabaseHelper dbHelper;
    public static boolean isReceiverAlive = false;
    public static final String APP_PREFERENCES = "my_settings";
    public static SharedPreferences mSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        HelperFactory.setHelper(getApplicationContext());
        Log.i("DEV_", "Create application!");
    }

    @Override
    public void onTrimMemory(int level) {
        Log.i("DEV_", "onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        Log.i("DEV_", "onTerminate");
        super.onTerminate();
    }

}