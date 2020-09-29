package com.example.walker.di.component;

import android.app.Application;
import android.content.Context;

import com.example.walker.App;
import com.example.walker.di.ApplicationContext;
import com.example.walker.di.module.ApplicationModule;
import com.example.walker.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

}