package com.example.walker.di.component;

import com.example.walker.di.PerActivity;
import com.example.walker.di.module.ActivityModule;
import com.example.walker.ui.filter.FilterActivity;
import com.example.walker.ui.login.LoginFragment;
import com.example.walker.ui.main.MainActivity;
import com.example.walker.ui.map.MapsFragment;
import com.example.walker.ui.offers.OffersFragment;
import com.example.walker.ui.product.ProductActivity;
import com.example.walker.ui.profile.ProfileFragment;
import com.example.walker.ui.registry.RegistryFragment;
import com.example.walker.ui.search.SearchActivity;
import com.example.walker.ui.splash.SplashActivity;
import com.example.walker.ui.store.StoreActivity;
import com.example.walker.ui.list.StoreListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginFragment fragment);

    void inject(SplashActivity activity);

    void inject(StoreActivity activity);

    void inject(SearchActivity activity);

    void inject(ProductActivity activity);

    void inject(FilterActivity activity);

    void inject(RegistryFragment fragment);

    void inject(MapsFragment fragment);

    void inject(OffersFragment fragment);

    void inject(StoreListFragment fragment);

    void inject(ProfileFragment fragment);
/*
    void inject(FeedActivity activity);

    void inject(AboutFragment fragment);

    void inject(OpenSourceFragment fragment);

    void inject(BlogFragment fragment);

    void inject(RateUsDialog dialog);*/

}
