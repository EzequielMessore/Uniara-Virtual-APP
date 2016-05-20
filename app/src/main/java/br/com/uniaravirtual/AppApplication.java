package br.com.uniaravirtual;

import android.app.Application;

import br.com.uniaravirtual.util.AppUtil;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.CONTEXT_APPLICATION = getApplicationContext();
    }
}
