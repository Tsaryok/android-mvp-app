package com.example.walker.di.component;

import com.example.walker.di.PerService;
import com.example.walker.di.module.ServiceModule;
import com.example.walker.service.SyncService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
