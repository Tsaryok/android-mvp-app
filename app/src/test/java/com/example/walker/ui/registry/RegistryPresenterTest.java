package com.example.walker.ui.registry;

import com.example.walker.data.DataManager;
import com.example.walker.ui.login.LoginMvpView;
import com.example.walker.ui.login.LoginPresenter;
import com.example.walker.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistryPresenterTest {

    @Mock
    RegistryMvpView mMockRegistryMvpView;
    @Mock
    DataManager mMockDataManager;

    private RegistryPresenter<RegistryMvpView> mRegistryPresenter;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mRegistryPresenter = new RegistryPresenter<>(
                mMockDataManager,
                testSchedulerProvider,
                compositeDisposable);
        mRegistryPresenter.onAttach(mMockRegistryMvpView);
    }

    @After
    public void tearDown() throws Exception {
        mRegistryPresenter.onDetach();
    }
}