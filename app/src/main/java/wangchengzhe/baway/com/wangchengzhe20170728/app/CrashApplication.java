package wangchengzhe.baway.com.wangchengzhe20170728.app;

import android.app.Application;

import wangchengzhe.baway.com.wangchengzhe20170728.ex.CrashHandler;

/**
 * 功能描述  :
 * 创建时间 : 2017/7/28 16:26
 * 编写人  :  王成哲
 */

public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}