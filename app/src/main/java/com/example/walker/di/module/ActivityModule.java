package com.example.walker.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.walker.di.ActivityContext;
import com.example.walker.di.PerActivity;
import com.example.walker.ui.filter.FilterMvpPresenter;
import com.example.walker.ui.filter.FilterMvpView;
import com.example.walker.ui.filter.FilterPresenter;
import com.example.walker.ui.login.LoginMvpPresenter;
import com.example.walker.ui.login.LoginMvpView;
import com.example.walker.ui.login.LoginPresenter;
import com.example.walker.ui.main.MainMvpPresenter;
import com.example.walker.ui.main.MainMvpView;
import com.example.walker.ui.main.MainPresenter;
import com.example.walker.ui.map.MapsMvpPresenter;
import com.example.walker.ui.map.MapsMvpView;
import com.example.walker.ui.map.MapsPresenter;
import com.example.walker.ui.offers.OffersMvpPresenter;
import com.example.walker.ui.offers.OffersMvpView;
import com.example.walker.ui.offers.OffersPresenter;
import com.example.walker.ui.product.ProductMvpPresenter;
import com.example.walker.ui.product.ProductMvpView;
import com.example.walker.ui.product.ProductPresenter;
import com.example.walker.ui.profile.ProfileMvpPresenter;
import com.example.walker.ui.profile.ProfileMvpView;
import com.example.walker.ui.profile.ProfilePresenter;
import com.example.walker.ui.registry.RegistryMvpPresenter;
import com.example.walker.ui.registry.RegistryMvpView;
import com.example.walker.ui.registry.RegistryPresenter;
import com.example.walker.ui.search.SearchMvpPresenter;
import com.example.walker.ui.search.SearchMvpView;
import com.example.walker.ui.search.SearchPresenter;
import com.example.walker.ui.splash.SplashMvpPresenter;
import com.example.walker.ui.splash.SplashMvpView;
import com.example.walker.ui.splash.SplashPresenter;
import com.example.walker.ui.store.StoreMvpPresenter;
import com.example.walker.ui.store.StoreMvpView;
import com.example.walker.ui.store.StorePresenter;
import com.example.walker.ui.list.StoreListMvpPresenter;
import com.example.walker.ui.list.StoreListMvpView;
import com.example.walker.ui.list.StoreListPresenter;
import com.example.walker.utils.rx.AppSchedulerProvider;
import com.example.walker.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MapsMvpPresenter<MapsMvpView> provideMapsPresenter(
            MapsPresenter<MapsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    StoreMvpPresenter<StoreMvpView> provideStorePresenter(
            StorePresenter<StoreMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProductMvpPresenter<ProductMvpView> provideProductPresenter(
            ProductPresenter<ProductMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RegistryMvpPresenter<RegistryMvpView> provideRegistryPresenter(
            RegistryPresenter<RegistryMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OffersMvpPresenter<OffersMvpView> provideOffersPresenter(
            OffersPresenter<OffersMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProfileMvpPresenter<ProfileMvpView> provideProfilePresenter(
            ProfilePresenter<ProfileMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SearchMvpPresenter<SearchMvpView> provideSearchPresenter(
            SearchPresenter<SearchMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FilterMvpPresenter<FilterMvpView> provideFilterPresenter(
            FilterPresenter<FilterMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    StoreListMvpPresenter<StoreListMvpView> provideStoreListPresenter(
            StoreListPresenter<StoreListMvpView> presenter) {
        return presenter;
    }


/*
    @Provides
    RatingDialogMvpPresenter<RatingDialogMvpView> provideRateUsPresenter(
            RatingDialogPresenter<RatingDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedMvpPresenter<FeedMvpView> provideFeedPresenter(
            FeedPresenter<FeedMvpView> presenter) {
        return presenter;
    }

    @Provides
    OpenSourceMvpPresenter<OpenSourceMvpView> provideOpenSourcePresenter(
            OpenSourcePresenter<OpenSourceMvpView> presenter) {
        return presenter;
    }

    @Provides
    BlogMvpPresenter<BlogMvpView> provideBlogMvpPresenter(
            BlogPresenter<BlogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedPagerAdapter provideFeedPagerAdapter(AppCompatActivity activity) {
        return new FeedPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    OpenSourceAdapter provideOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSourceResponse.Repo>());
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<BlogResponse.Blog>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

 */
}
