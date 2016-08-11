package com.blogspot.e_kanivets.moneytracker;

import android.app.Application;

import com.blogspot.e_kanivets.moneytracker.di.AppComponent;
import com.blogspot.e_kanivets.moneytracker.di.DaggerAppComponent;
import com.blogspot.e_kanivets.moneytracker.di.module.ControllerModule;
import com.blogspot.e_kanivets.moneytracker.di.module.repo.CachedRepoModule;

import timber.log.Timber;

/**
 * Custom application implementation.
 * Created on 29/08/14.
 *
 * @author Evgenii Kanivets
 */
public class MtApp extends Application {

    private static MtApp mtApp;

    public static MtApp get() {
        return mtApp;
    }

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        mtApp = this;
        component = buildComponent();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        else Timber.plant(new ReleaseTree());
    }

    public AppComponent getAppComponent() {
        return component;
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .cachedRepoModule(new CachedRepoModule(get()))
                .controllerModule(new ControllerModule(get()))
                .build();
    }

    private static class ReleaseTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            // Do nothing fot now
        }
    }
}